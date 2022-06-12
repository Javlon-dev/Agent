package com.company.service;

import com.company.dto.AgentDTO;
import com.company.dto.request.AgentRegistrationDTO;
import com.company.entity.AgentEntity;
import com.company.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AgentService agentService;

    private final LoggedDatesService loggedDatesService;

    private final AgentRepository agentRepository;

    public AgentDTO agentRegistration(AgentRegistrationDTO dto) {
        return agentService.create(dto);
    }

    public Boolean agentLogin() {
        AgentEntity entity = new AgentEntity();
        // TODO: 12-Jun-22 Login 
        return loggedDatesService.login(entity);
    }

    public Boolean agentLogout() {
        AgentEntity entity = new AgentEntity();
        // TODO: 12-Jun-22 Logout 
        return loggedDatesService.logout(entity);
    }

}
