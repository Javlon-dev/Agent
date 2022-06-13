package com.company.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class LoggedDatesDTO {

    private AgentDTO agent;

    private LocalDateTime loginDate;

    private LocalDateTime logoutDate;

}
