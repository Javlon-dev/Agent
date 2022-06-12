package com.company.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class AgentRegistrationDTO {

    @NotBlank(message = "Firstname required")
    @Length(min = 2, message = "Firstname length must be between 2 to more than")
    private String firstname;

    @NotBlank(message = "Lastname required")
    @Length(min = 2, message = "Lastname length must be between 2 to more than")
    private String lastname;

    @NotBlank(message = "Nickname required")
    @Length(min = 5, message = "Nickname length must be between 5 to more than")
    private String nickname;

}
