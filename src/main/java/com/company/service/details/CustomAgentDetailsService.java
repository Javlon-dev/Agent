package com.company.service.details;

import com.company.config.details.CustomUserDetails;
import com.company.entity.AgentEntity;
import com.company.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAgentDetailsService implements UserDetailsService {

    private final AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        AgentEntity entity = agentRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Agent Not Found id={}", id);
                    return new UsernameNotFoundException("Agent Not Found!");
                });

        return new CustomUserDetails(entity);
    }
}
