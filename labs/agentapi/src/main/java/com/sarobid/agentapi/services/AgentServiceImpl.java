package com.sarobid.agentapi.services;

import org.springframework.stereotype.Service;
import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.RunConfig;
import com.google.adk.events.Event;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.genai.types.Content;
import com.google.genai.types.Part;

import io.reactivex.rxjava3.core.Flowable;
import java.util.Scanner;

import com.sarobid.agentapi.agent.mathtutor.MathTutorAgent;
import com.sarobid.agentapi.dto.AgentRequestDTO;
import com.sarobid.agentapi.dto.AgentResponseDTO;

@Service
public class AgentServiceImpl implements AgentService{

    private RunConfig runConfig;
    private InMemoryRunner runner;
    private Session session;

    public AgentServiceImpl() {

        this.runner =
                new InMemoryRunner(
                        MathTutorAgent.initAgent()
                );

        this.runConfig =
                RunConfig.builder().build();

        this.session =
                runner.sessionService()
                        .createSession(
                                runner.appName(),
                                "user123"
                        )
                        .blockingGet();
    }

    @Override
    public AgentResponseDTO requestAgent(AgentRequestDTO agentRequestDTO) {
        try {

            Content userMessage =
                    Content.fromParts(
                            Part.fromText(agentRequestDTO.getMessage())
                    );

            Flowable<Event> events =
                    runner.runAsync(
                            session.userId(),
                            session.id(),
                            userMessage,
                            runConfig
                    );

            StringBuilder response =
                    new StringBuilder();

            events.blockingForEach(event -> {

                if (event.finalResponse()) {
                    response.append(
                            event.stringifyContent()
                    );
                }
            });

            return new AgentResponseDTO(response.toString());

        } catch (Exception e) {

            throw e;
        }
    }
    
}
