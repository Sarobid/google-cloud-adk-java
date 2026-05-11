
import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.agents.SequentialAgent;
import com.google.adk.tools.Annotations.Schema;

import agent.GoogleSearchAgent;
import agent.TranslatorAgent;

import com.google.adk.tools.FunctionTool;

import java.util.Map;

public class AgentSequentiel {

    public static SequentialAgent initAgent() {
        GoogleSearchAgent googleSearchAgent = new GoogleSearchAgent();
        TranslatorAgent translatorAgent = new TranslatorAgent();
        return 
        SequentialAgent.builder()
            .name("sequential_agent")
            .description("Executes a sequence of translate and search google")
            .subAgents(translatorAgent.getAgent(),googleSearchAgent.getAgent(),translatorAgent.getAgent())
            .build();
    }
}
