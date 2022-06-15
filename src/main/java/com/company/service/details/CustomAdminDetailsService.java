package com.company.service.details;

import com.company.config.details.CustomUserDetails;
import com.company.entity.AgentEntity;
import com.company.entity.ProfileEntity;
import com.company.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAdminDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        ProfileEntity entity = profileRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Admin Not Found id={}", id);
                    return new UsernameNotFoundException("Admin Not Found!");
                });

        return new CustomUserDetails(entity);
    }
}
