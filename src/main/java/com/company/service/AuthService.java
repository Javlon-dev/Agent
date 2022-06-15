package com.company.service;

import com.company.config.details.CustomUserDetails;
import com.company.config.details.EntityDetails;
import com.company.dto.AgentDTO;
import com.company.dto.request.AgentLoginDTO;
import com.company.dto.request.AgentRegistrationDTO;
import com.company.entity.AgentEntity;
import com.company.entity.ProfileEntity;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AgentService agentService;

    private final ProfileRepository profileRepository;

    private final PasswordEncoder passwordEncoder;

    public AgentDTO agentRegistration(AgentRegistrationDTO dto) {
        return agentService.create(dto);
    }

    public AgentDTO agentLogin(AgentLoginDTO dto) {

        if (dto.getNickname().startsWith("ADMIN")) {
            ProfileEntity profile = profileRepository
                    .findByEmailAndPassword(dto.getNickname(), dto.getPassword())
                    .orElseThrow(() -> {
                        log.warn("Invalid Admin Credentials email={}", dto.getNickname());
                        return new BadCredentialsException("Invalid Admin Credentials!");
                    });

            return new AgentDTO(JwtUtil.encode(profile.getId(), profile.getRole()));

        } else {

            AgentEntity entity = agentService.getByNicknameAndPassword(dto.getNickname(), dto.getPassword());

            String token = JwtUtil.encode(entity.getId(), entity.getRole());
            agentService.login(token, entity.getId());

            AgentDTO agentDTO = agentService.toDTO(entity);
            agentDTO.setToken(token);
            return agentDTO;
        }

    }

    public Boolean agentLogout() {
        CustomUserDetails customUser = EntityDetails.getCustomUser();
        agentService.logout(customUser.getId());
        return true;
    }

}
