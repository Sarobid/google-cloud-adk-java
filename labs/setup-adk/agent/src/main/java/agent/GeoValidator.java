package agent;

import java.util.Map;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.genai.types.Schema;

public class GeoValidator {
    private BaseAgent agent = null;

    private Schema CAPITAL_OUTPUT = null;

    public GeoValidator(){
        this.initCAPTITAL_OUTPUT();
        this.initAgent();
    }
    public BaseAgent getAgent(){
        return this.agent;
    }
    private void initCAPTITAL_OUTPUT(){
        this.CAPITAL_OUTPUT = Schema.builder()
            .type("OBJECT")
            .description("Schema for capital city information.")
            .properties(
                Map.of(
                    "capital",
                    Schema.builder()
                        .type("STRING")
                        .description("The capital city of the country.")
                        .build()))
            .build();
    }

    private void initAgent() {
        this.agent =  LlmAgent.builder()
            .name("geo_validator")
            .description("Answer questions for capital city")
            .instruction("""
                     Answer questions
                """)
            .model("gemini-2.5-flash")
            .outputSchema(CAPITAL_OUTPUT)
            .build();
    }
}
