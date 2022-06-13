package com.company.config.details;

import com.company.entity.AgentEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class EntityDetails {

    public static AgentEntity getAgent() {
        CustomEntityToken details = (CustomEntityToken) SecurityContextHolder.getContext().getAuthentication();
//        CustomProfileDetails details = (CustomProfileDetails) authentication.getPrincipal();
        return details.getAgent();
    }

}
