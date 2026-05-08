package com.sarobid.agentapi.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sarobid.agentapi.dto.AgentRequestDTO;
import com.sarobid.agentapi.dto.AgentResponseDTO;
import com.sarobid.agentapi.services.IAgentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/agent")
public class AgentRestController {
    private IAgentService agentService;

    @Autowired
    public AgentRestController(IAgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("")
    public AgentResponseDTO postChat(@RequestBody AgentRequestDTO agentRequestDTO) {
        return this.agentService.requestAgent(agentRequestDTO);
    }
    





}
