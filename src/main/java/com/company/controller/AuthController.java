package com.company.controller;

import com.company.dto.AgentDTO;
import com.company.dto.request.AgentRegistrationDTO;
import com.company.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Api(tags = "Agent Authorization")
public class AuthController {


    private final AuthService authService;


    @ApiOperation(value = "Agent Registration", notes = "Method used for registration agent (for ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registration")
    public ResponseEntity<AgentDTO> agentRegistration(@RequestBody @Valid AgentRegistrationDTO dto) {
        log.info("Agent Registration {}", dto);
        return ResponseEntity.ok(authService.agentRegistration(dto));
    }

    @ApiOperation(value = "Agent Login", notes = "Method used to login for agent only (for AGENT)")
    @PreAuthorize("hasRole('AGENT')")
    @PostMapping("/login")
    public ResponseEntity<Boolean> agentLogin() {
        log.info("Agent Login");
        return ResponseEntity.ok(authService.agentLogin());
    }

    @ApiOperation(value = "Agent Logout", notes = "Method used to logout for agent only (for AGENT)")
    @PreAuthorize("hasRole('AGENT')")
    @PostMapping("/logout")
    public ResponseEntity<Boolean> agentLogout() {
        log.info("Agent Logout");
        return ResponseEntity.ok(authService.agentLogout());
    }

}
