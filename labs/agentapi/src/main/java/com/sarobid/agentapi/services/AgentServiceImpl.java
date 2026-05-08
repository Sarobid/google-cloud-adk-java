package com.sarobid.agentapi.services;

import org.springframework.stereotype.Service;

import com.sarobid.agentapi.dto.AgentRequestDTO;
import com.sarobid.agentapi.dto.AgentResponseDTO;

@Service
public class AgentServiceImpl implements AgentService{

    @Override
    public AgentResponseDTO requestAgent(AgentRequestDTO agentRequestDTO) {
        return new AgentResponseDTO("Request me");
    }
    
}
