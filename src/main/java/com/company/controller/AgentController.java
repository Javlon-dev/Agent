package com.company.controller;

import com.company.dto.AgentDTO;
import com.company.dto.LoggedDatesDTO;
import com.company.dto.request.AgentBioDTO;
import com.company.service.AgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/agent")
@RequiredArgsConstructor
@Api(tags = "Agent Controller")
public class AgentController {


    private final AgentService agentService;

    /**
     * AGENT
     */

    @ApiOperation(value = "Agent Info", notes = "Method used to show agent information (for AGENT)")
    @PreAuthorize("hasRole('AGENT')")
    @GetMapping("")
    public ResponseEntity<AgentDTO> getInfo() {
        log.info("Agent Info");
        return ResponseEntity.ok(agentService.getInfo());
    }

    @ApiOperation(value = "Update Agent Bio", notes = "Method used for update agent bio (for AGENT)")
    @PreAuthorize("hasRole('AGENT')")
    @PutMapping("/bio")
    public ResponseEntity<AgentDTO> updateBio(@RequestBody @Valid AgentBioDTO dto) {
        log.info("Update Agent Bio {}", dto);
        return ResponseEntity.ok(agentService.updateBio(dto));
    }

    @ApiOperation(value = "Get Agent Logs Date", notes = "Method used to show agent login and logout dates (for AGENT)")
    @PreAuthorize("hasRole('AGENT')")
    @GetMapping("/list")
    public ResponseEntity<PageImpl<LoggedDatesDTO>> pagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Get Agent Logs Date page={} size={}", page, size);
        return ResponseEntity.ok(agentService.pagination(page, size));
    }

}
