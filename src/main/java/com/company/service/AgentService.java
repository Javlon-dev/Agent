package com.company.service;

import com.company.config.details.CustomUserDetails;
import com.company.config.details.EntityDetails;
import com.company.dto.AgentDTO;
import com.company.dto.request.AgentBioDTO;
import com.company.dto.request.AgentRegistrationDTO;
import com.company.entity.AgentEntity;
import com.company.enums.Role;
import com.company.exception.ItemAlreadyExistsException;
import com.company.repository.AgentRepository;
import com.company.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgentService {


    private final AgentRepository agentRepository;

    private final PasswordEncoder passwordEncoder;


    public AgentDTO create(AgentRegistrationDTO dto) {
        Optional<AgentEntity> agent = agentRepository.findByNickname(dto.getNickname());

        if (agent.isPresent()) {
            log.warn("Agent Exists nickname={}", dto.getNickname());
            throw new ItemAlreadyExistsException("Agent Exists!");
        }

        AgentEntity entity = new AgentEntity();
        entity.setFirstname(dto.getFirstname());
        entity.setLastname(dto.getLastname());
        entity.setNickname(dto.getNickname());
        entity.setRole(Role.ROLE_AGENT);

        String password = PasswordUtil.generatePassword();
        entity.setPassword(passwordEncoder.encode(password + dto.getNickname()));

        agentRepository.save(entity);

        AgentDTO agentDTO = toDTO(entity);
        agentDTO.setPassword(password);
        return agentDTO;
    }

    public AgentDTO getInfo() {
        CustomUserDetails customUser = EntityDetails.getCustomUser();
        return new AgentDTO(customUser.getFirstname(), customUser.getLastname(), customUser.getNickname());
    }

    public AgentDTO updateBio(AgentBioDTO dto) {
        CustomUserDetails customUser = EntityDetails.getCustomUser();

        customUser.setFirstname(dto.getFirstname());
        customUser.setLastname(dto.getLastname());
        agentRepository.updateBio(dto.getFirstname(), dto.getLastname(), customUser.getId());

        return new AgentDTO(customUser.getFirstname(), customUser.getLastname(), customUser.getNickname());
    }

    public PageImpl<AgentDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<AgentEntity> entityPage = agentRepository.findAll(pageable);

        List<AgentDTO> dtoList = entityPage
                .map(this::toDTO)
                .toList();

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public AgentEntity getByNicknameAndPassword(String nickname, String password) {
        return agentRepository
                .findByNicknameAndPassword(nickname,
                        passwordEncoder.encode(password + nickname))
                .orElseThrow(() -> {
                    log.warn("Invalid Agent Credentials nickname={}", nickname);
                    return new BadCredentialsException("Invalid Agent Credentials!");
                });
    }

    public AgentDTO toDTO(AgentEntity entity) {
        AgentDTO dto = new AgentDTO();
        dto.setFirstname(entity.getFirstname());
        dto.setLastname(entity.getLastname());
        dto.setNickname(entity.getNickname());
        return dto;
    }

    public void login(String token, String id) {
        agentRepository.updateTokenAndLogin(token, LocalDateTime.now(), id);
    }

    public void logout(String id) {
        agentRepository.updateLogout(LocalDateTime.now(), id);
    }

}
