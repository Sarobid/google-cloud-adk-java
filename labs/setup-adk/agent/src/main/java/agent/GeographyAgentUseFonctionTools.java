package agent;

import java.util.HashMap;
import java.util.Map;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.tools.FunctionTool;

import autovalue.shaded.com.google.common.collect.ImmutableList;

public class GeographyAgentUseFonctionTools {
    private BaseAgent agent = null;

    public GeographyAgentUseFonctionTools(){
        this.initAgent();
    }
    public BaseAgent getAgent(){
        return this.agent;
    }


    private void initAgent(){
        this.agent = LlmAgent.builder()
        .name("geography_assistant")
        .description("Aide les utilisateurs à en apprendre davantage sur la géographie\n" + //
                        "mondiale.")
        .instruction("""
                Tu es un assistant de géographie qui aide les utilisateurs à découvrir les
                    capitales du monde.
                    Lorsqu'un utilisateur pose une question sur une capitale :
                    Utilise l'outil getCapitalCity pour trouver la réponse.
                        1. si getCapitalCity retourne un status 'success' fournie le city à l'utilisateur
                            - Fournis les informations de manière conviviale et pédagogique.
                            - Tu peux ajouter des faits intéressants si tu en connais.
                        2. Si l'outil retourne un status 'error', indique poliment à l'utilisateur que tu ne
                disposes pas de cette information.
                """)
         .model("gemini-2.5-flash")
        .tools(ImmutableList.of(FunctionTool.create(GeographyAgentUseFonctionTools.class, "getCapitalCity")))
        .build();
    }

    public static Map<String,String> getCapitalCity(@Schema(name = "country") String country){
        HashMap<String,String> cityHashMap = new HashMap<>();
        cityHashMap.put("france", "Paris");
        cityHashMap.put("japan", "Tokyo");
        cityHashMap.put("canada", "Ottawa");
        cityHashMap.put("germany", "Berlin");
        cityHashMap.put("brazil", "Brasília");
        cityHashMap.put("australia", "Canberra");
        cityHashMap.put("india", "New Delhi");
        cityHashMap.put("mexico", "Mexico City");
        String resp = cityHashMap.get(country.toLowerCase());
        Map<String,String> resMap = new HashMap<>();
        if (resp == null) {
            resMap.put("status", "error");
            resMap.put("error_mesage", "Désolé, je n'ai pas d'informations sur la capitale du pays suivant : "+country);
            return resMap;
        }
        resMap.put("status", "success");
        resMap.put("city", resp);
        return resMap;
    }


}
