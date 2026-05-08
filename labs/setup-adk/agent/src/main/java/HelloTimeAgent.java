
import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.tools.FunctionTool;

import java.util.Map;

public class HelloTimeAgent {

    public static BaseAgent initAgent() {
        return LlmAgent.builder()
            .name("agent serviable")
            .description("Un agent assistant serviable.")
            .instruction("""
                Tu es un assistant serviable
                """)
            .model("gemini-2.5-flash")
            .tools(FunctionTool.create(HelloTimeAgent.class, "getCurrentTime"))
            .build();
    }

    /** Mock tool implementation */
    @Schema(description = "Get the current time for a given city")
    public static Map<String, String> getCurrentTime(
        @Schema(name = "city", description = "Name of the city to get the time for") String city) {
        return Map.of(
            "city", city,
            "forecast", "The time is 10:30am."
        );
    }
}
