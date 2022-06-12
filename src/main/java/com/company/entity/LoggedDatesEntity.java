package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logged_dates")
@Getter
@Setter
public class LoggedDatesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agent_id", nullable = false)
    private String agentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", updatable = false, insertable = false)
    private AgentEntity agent;

    @Column(name = "login_date")
    private LocalDateTime loginDate;

    @Column(name = "logout_date")
    private LocalDateTime logoutDate;

}
