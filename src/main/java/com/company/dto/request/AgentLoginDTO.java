package com.company.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class AgentLoginDTO {

    @NotBlank(message = "Nickname required")
    @Length(min = 5, message = "Nickname length must be between 5 to more than")
    private String nickname;

    @NotBlank(message = "Password required")
    private String password;

}
