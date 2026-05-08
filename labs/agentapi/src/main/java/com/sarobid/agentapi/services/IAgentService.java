package com.sarobid.agentapi.services;

import com.sarobid.agentapi.dto.AgentRequestDTO;
import com.sarobid.agentapi.dto.AgentResponseDTO;

public interface IAgentService {
    public AgentResponseDTO requestAgent(AgentRequestDTO agentRequestDTO);
}
