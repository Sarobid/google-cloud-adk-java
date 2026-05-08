package com.sarobid.agentapi.agent.mathtutor;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;

public class MathTutorAgent {
    public static BaseAgent initAgent() {
        return LlmAgent.builder()
            .name("math_tutor_agent")
            .description("Aide les élèves à apprendre l'algèbre en les guidant à travers les\n" + //
                                "étapes de résolution de problèmes.")
            .instruction("""
                    Tu es un professeur de mathématiques patient. Aide les élèves sur
                leurs problèmes d'algèbre.
                """)
            .model("gemini-2.0-flash-lite")
            .build();
    }
}
