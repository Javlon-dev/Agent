package com.company.config.details;

import com.company.entity.AgentEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@Getter
@Setter
public class CustomUserDetails implements UserDetails {

    private String id;

    private String firstname;

    private String lastname;

    private String nickname;

    private String password;

    private Role role;

    private String token;

    public CustomUserDetails(AgentEntity entity) {
        this.id = entity.getId();
        this.firstname = entity.getFirstname();
        this.lastname = entity.getLastname();
        this.nickname = entity.getNickname();
        this.password = entity.getPassword();
        this.token = entity.getToken();
        this.role = entity.getRole();
    }

    public CustomUserDetails(ProfileEntity entity) {
        this.id = entity.getId();
        this.nickname = entity.getEmail();
        this.password = entity.getPassword();
        this.role = entity.getRole();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
