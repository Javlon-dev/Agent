package com.company.service.bin;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final ProfileRepository profileRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${message.admin.email}")
    private String adminEmail;

    @Value("${message.admin.password}")
    private String adminPassword;

    public void createAdmin() {
        if (profileRepository.findByEmail("ADMIN" + adminEmail).isPresent()) {
            return;
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setEmail("ADMIN" + adminEmail);
        entity.setPassword(adminPassword);
        entity.setRole(ProfileRole.ROLE_ADMIN);

        profileRepository.save(entity);
    }
}
