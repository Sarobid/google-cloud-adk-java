package agent;

import com.fasterxml.jackson.databind.JsonSerializable.Base;
import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.GoogleSearchTool;
import com.google.common.collect.ImmutableList;

public class GoogleSearchAgent {
    private BaseAgent agent = null;
    private GoogleSearchTool googleSearchTool = null;

    public GoogleSearchAgent(){
        this.googleSearchTool = new GoogleSearchTool();
        this.initAgent();
    }

    public BaseAgent getAgent(){
        return this.agent;
    }
    private void initAgent() {
        this.agent =  LlmAgent.builder()
            .name("google_search_agent")
            .description("Answer questions using Google Search.")
            .instruction("""
                     You are an experienced researcher.

                    Use ONLY the translated text stored in:
                    state.translate_text 
                    
                    ignore the user’s original message.

                    Use the translated text as a Google search query.
                    Stick strictly to factual answers.
                """)
            .model("gemini-2.5-flash")
            .tools(ImmutableList.of(this.googleSearchTool))
            .build();
    }
}
