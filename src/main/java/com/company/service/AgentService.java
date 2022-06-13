package com.company.service;

import com.company.config.details.EntityDetails;
import com.company.dto.AgentDTO;
import com.company.dto.LoggedDatesDTO;
import com.company.dto.request.AgentBioDTO;
import com.company.dto.request.AgentRegistrationDTO;
import com.company.entity.AgentEntity;
import com.company.enums.ProfileRole;
import com.company.exception.ItemAlreadyExistsException;
import com.company.repository.AgentRepository;
import com.company.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgentService {


    private final AgentRepository agentRepository;

    private final LoggedDatesService loggedDatesService;


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
        entity.setRole(ProfileRole.ROLE_AGENT);

        String password = PasswordUtil.generatePassword();
        entity.setPassword(DigestUtils.sha256Hex(password + dto.getNickname()));

        agentRepository.save(entity);

        AgentDTO agentDTO = toDTO(entity);
        agentDTO.setPassword(password);
        return agentDTO;
    }

    public AgentDTO getInfo() {
        AgentEntity entity = EntityDetails.getAgent();
        return toDTO(entity);
    }

    public AgentDTO updateBio(AgentBioDTO dto) {
        AgentEntity entity = EntityDetails.getAgent();
        entity.setFirstname(dto.getFirstname());
        entity.setLastname(dto.getLastname());

        agentRepository.updateBio(dto.getFirstname(), dto.getLastname(), entity.getId());

        return toDTO(entity);
    }

    public PageImpl<LoggedDatesDTO> pagination(int page, int size) {
        AgentEntity entity = EntityDetails.getAgent();

        return loggedDatesService.pagination(page, size, entity.getNickname());
    }

    public AgentDTO toDTO(AgentEntity entity) {
        AgentDTO dto = new AgentDTO();
        dto.setFirstname(entity.getFirstname());
        dto.setLastname(entity.getLastname());
        dto.setNickname(entity.getNickname());
        return dto;
    }

}
