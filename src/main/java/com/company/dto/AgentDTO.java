package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentDTO {

    private String firstname;

    private String lastname;

    private String nickname;

    private String password;

    private String token;

    public AgentDTO(String firstname, String lastname, String nickname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
    }

    public AgentDTO(String token) {
        this.token = token;
    }
}
