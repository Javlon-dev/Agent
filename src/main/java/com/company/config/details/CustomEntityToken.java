package com.company.config.details;

import com.company.entity.AgentEntity;
import com.company.entity.ProfileEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class CustomEntityToken extends UsernamePasswordAuthenticationToken {

    private AgentEntity entity;

    private ProfileEntity profile;

    public CustomEntityToken(Object principal, Object credentials,
                             Collection<? extends GrantedAuthority> authorities,
                             ProfileEntity profile) {
        super(principal, credentials, authorities);
        this.profile = profile;
    }

    public CustomEntityToken(Object principal, Object credentials,
                             Collection<? extends GrantedAuthority> authorities,
                             AgentEntity entity) {
        super(principal, credentials, authorities);
        this.entity = entity;
    }

    public AgentEntity getAgent() {
        return entity;
    }

    public ProfileEntity getProfile() {
        return profile;
    }

}
