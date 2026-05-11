package agent;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;

public class MathTutorAgent {
    public static BaseAgent initAgent() {
        return LlmAgent.builder()
            .name("math_tutor_agent")
            .description("Aide les élèves à apprendre l'algèbre en les guidant à travers les\n" + //
                                "étapes de résolution de problèmes.")
            .instruction("""
                     Tu es un professeur d'algèbre patient et encourageant.
                        Ton approche pédagogique :
                        1. Lorsqu'un élève pose une question, commence par identifier ce qui le met en
                        difficulté
                        2. Décompose le problème en étapes plus petites et gérables
                        3. Guide l'élève pour qu'il découvre la réponse plutôt que de la lui donner
                        directement
                        4. Encourage positivement les efforts et les progrès de l'élève
                        5. Utilise un langage simple et évite le jargon
                        Garde toujours un ton bienveillant et patient. L'apprentissage prend du temps, et
                        chaque
                        question est une opportunité de progresser
                """)
            .model("gemini-2.5-flash")
            .build();
    }
}
