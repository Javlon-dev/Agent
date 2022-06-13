package com.company.config.provider;

import com.company.config.details.CustomEntityToken;
import com.company.entity.AgentEntity;
import com.company.entity.ProfileEntity;
import com.company.repository.AgentRepository;
import com.company.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AgentRepository agentRepository;

    private final ProfileRepository profileRepository;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String nickname = authentication.getName();
        String password = authentication.getCredentials().toString();

        log.info("Agent Request nickname={}", nickname);

        AgentEntity entity;
        ProfileEntity profile;

        if (nickname.startsWith("ADMIN")) {
            profile = profileRepository
                    .findByEmailAndPassword(nickname, password)
                    .orElseThrow(() -> {
                        log.warn("Admin Not Found email={}", nickname);
                        return new UsernameNotFoundException("Admin Not Found!");
                    });

            return new CustomEntityToken(nickname, password,
                    Collections.singletonList(new SimpleGrantedAuthority(profile.getRole().name())), profile);

        } else {

            password = DigestUtils.sha256Hex(password + nickname);

            entity = agentRepository
                    .findByNicknameAndPassword(nickname, password)
                    .orElseThrow(() -> {
                        log.warn("Agent Not Found nickname={}", nickname);
                        return new UsernameNotFoundException("Agent Not Found!");
                    });


            return new CustomEntityToken(nickname, password,
                    Collections.singletonList(new SimpleGrantedAuthority(entity.getRole().name())), entity);
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
