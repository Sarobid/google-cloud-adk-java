package agent;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;

public class NameExtractorAgent {

    public static BaseAgent ROOT_AGENT = new NameExtractorAgent().getAgent();
    private BaseAgent agent = null;
    public NameExtractorAgent(){
        this.initAgent();
    }
    public BaseAgent getAgent(){
        return this.agent;  
    }
    public static String getOutputKey(){
        return "user_name";
    }

    private void initAgent() {
        this.agent = LlmAgent.builder()
            .name("name_extractor_agent")
            .model("gemini-2.5-flash")
            .description("Un agent simple qui extrait les noms d'une phrase donnée.")
            .instruction("""
                "Extrais du message le nom de la personne. Affiche UNIQUEMENT le nom,
rien d'autre.
            """)
            .outputKey(getOutputKey())
            .build();
    }
}
