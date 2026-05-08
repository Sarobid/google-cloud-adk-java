
import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.tools.FunctionTool;

import java.util.Map;

public class Agent {

    public static BaseAgent initAgent() {
        return LlmAgent.builder()
            .name("math_tutor_agent")
            .description("Aide les élèves à apprendre l'algèbre en les guidant à travers les\n" + //
                                "étapes de résolution de problèmes.")
            .instruction("""
                    Tu es un professeur de mathématiques patient. Aide les élèves sur
                leurs problèmes d'algèbre.
                """)
            .model("gemini-2.5-flash")
            .build();
    }
}
