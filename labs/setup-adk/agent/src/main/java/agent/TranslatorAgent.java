package agent;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;

public class TranslatorAgent {
    private BaseAgent agent = null;
    public static String outputKey = "translate_text";
    public TranslatorAgent(){
        this.initAgent();
    }

    public BaseAgent getAgent(){
        return this.agent;
    }
    private void initAgent() {
        this.agent =  LlmAgent.builder()
            .name("translator_agent")
            .description("Translate into English OR MALAGASY")
            .instruction("""
                    You are a translation agent.

                Rules:
                - Automatically detect the language of the text received.
                - If the text is in English, translate the entire content into Malagasy.
                - If the text is in Malagasy, translate the entire content into English.
                - Do not provide any explanation.
                - Do not add any comments.
                - Never say ‘Here is the translation’.
                - Return only the translated text.
                - Preserve the meaning, tone and punctuation of the original text.
                """)
            .model("gemini-2.5-flash")
            .outputKey(outputKey)
            .build();
    }
}
