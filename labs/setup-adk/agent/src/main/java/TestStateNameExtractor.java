import com.google.genai.types.Content;
import com.google.adk.events.Event;

import java.util.Optional;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.RunConfig;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.runner.Runner;
import com.google.adk.sessions.InMemorySessionService;
import com.google.adk.sessions.Session;
import com.google.genai.types.Part;

import agent.NameExtractorAgent;
import agent.TravelAgent;
import io.reactivex.rxjava3.core.Flowable;
public class TestStateNameExtractor {
    public static void main(String[] args) {

        String appName = "extract_name_app";
        String userId = "test_user";
        String sessionId = "session1";
        RunConfig runConfig = RunConfig.builder().build();

        BaseAgent ROOT_AGENT = NameExtractorAgent.ROOT_AGENT;
        InMemorySessionService inMemorySessionService = new InMemorySessionService();
        Runner runner = Runner.builder()
            .agent(ROOT_AGENT)
            .appName(appName)
            .sessionService(inMemorySessionService)
            .build();
        Session session = inMemorySessionService.createSession(appName, userId, null, sessionId).blockingGet();
        System.out.println(session.id());
        System.out.println(session.appName());
        Content userMsg = Content.fromParts(Part.fromText("Hi, my name is Andry Malala"));

        System.out.println("=== Exécution de l'agent en cours ===");
        try{
            Flowable<Event> events = runner.runAsync(userId, sessionId, userMsg, runConfig);
            for (Event event : events.blockingIterable()) {
                if (event.finalResponse()) {
                    String reponse  = event.stringifyContent();
                    System.out.println("Agent response:"+reponse);
                }   
            }
            Session updatedSession =
        inMemorySessionService.getSession(appName, userId, sessionId, Optional.empty()).blockingGet();
             System.out.println("\\nState still contains: "+updatedSession.state().get(NameExtractorAgent.getOutputKey()));
             System.out.println("L'état persiste d'un tour à l'autre");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
