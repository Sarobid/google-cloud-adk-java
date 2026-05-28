package agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonSerializable.Base;
import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.tools.FunctionTool;

public class TravelAgent {

    public static BaseAgent ROOT_AGENT = new TravelAgent().getAgent();
    private BaseAgent agent = null;

    public TravelAgent(){
        this.initAgent();
    }
    private void initAgent(){
        this.agent = LlmAgent.builder()
            .name("travel_agent")
            .description("Aide les utilisateurs à planifier leurs voyages en trouvant des vols\n" + //
                                "et des hôtels.")
            .instruction("""
                u es un agent de voyage serviable.
 Tes capacités :
 - Rechercher des vols à l'aide de searchFlights(destination, departure_date)
 - Rechercher des hôtels à l'aide de searchHotels(city, check_in_date)
 - Calculer des budgets de voyage à l'aide de calculateTripBudget(flight_price,
hotel_price, num_nights)
 Lorsque tu aides les utilisateurs, procède comme suit :
 1. S'ils posent des questions sur les vols, utilise searchFlights.
 2. S'ils posent des questions sur les hôtels, utilise searchHotels.
 3. S'ils souhaitent une estimation complète du voyage, utilise les deux outils de
recherche, puis calculateTripBudget.
 4. Présente toujours clairement les différentes options et leurs prix.
 5. Si un outil renvoie une erreur, présente tes excuses et suggère d'autres
destinations disponibles (Paris ou Tokyo).
 Sois aimable et aide les utilisateurs à planifier leur voyage idéal !
            """)
            .model("gemini-2.5-flash")
            .tools(List.of(
                FunctionTool.create(this, "searchFlights"),
                FunctionTool.create(this, "searchHotels"),
                FunctionTool.create(this, "calculateTripBudget")
            ))
            .build();
    }

    public BaseAgent getAgent(){
        return this.agent;
    }

    @Schema(name = "searchFlights", description ="Recherche les vols disponibles vers une destination à une date précise.\n" + //
                " Utilise cet outil lorsqu'un client souhaite connaître les options de vol.\n" + //
                " Args:\n" + //
                " destination (str) : la ville de destination (par exemple, \"Paris\", \"Tokyo\").\n" + //
                " departure_date (str) : date de départ au format AAAA-MM-JJ.\n" + //
                " Renvoie :\n" + //
                " dict : résultats de la recherche de vols.\n" + //
                " En cas de succès : {'status': 'success', 'flights': [...], 'count': N}\n" + //
                " En cas d'erreur : {'status': 'error', 'error_message': 'explanation'")
    public Map<String, Object> searchFlights(
        @Schema(name = "destination", description = "La ville de destination (par exemple, \"Paris\", \"Tokyo\").") String destination,
        @Schema(name = "departure_date", description = "Date de départ au format AAAA-MM-JJ.") String departure_date
    ) {
        HashMap<String,List<HashMap<String, Object>>> availableFlights = new HashMap<>();
        availableFlights.put("paris", List.of(
            new HashMap<>(){{
                put("flight_number", "AF123");
                put("price_usd", 450);
                put("duration_hours", 8);
            }},
            new HashMap<>(){{
                put("flight_number", "BA456");
                put("price_usd", 480);
                put("duration_hours", 7.5);
            }}
        ));
        availableFlights.put("tokyo", List.of(
            new HashMap<>(){{
                put("flight_number", "JL789");
                put("price_usd", 850);
                put("duration_hours", 13);
            }},
            new HashMap<>(){{
                put("flight_number", "ANA101");
                put("price_usd", 820);
                put("duration_hours", 12.5);
            }}
        ));
        String destkey = destination.toLowerCase();
        if (!availableFlights.containsKey(destkey)) {
            return Map.of(
                "status", "error",
                "error_message", "Aucun vol trouvé pour " + destination + ". Essayez Paris ou Tokyo."
            );
        }
        return Map.of(
            "status", "success",
            "destination", destination,
            "departure_date", departure_date,
            "flights", availableFlights.get(destkey),
            "count", availableFlights.get(destkey).size()
            );
    }

    @Schema(name = "searchHotels", description = "Recherche des hôtels disponibles dans une ville pour une date d'arrivée\n" + //
                "spécifique.\n" + //
                " Utilise cet outil lorsqu'un client a besoin d'un hébergement.\n" + //
                " Args:\n" + //
                " city (str) : le nom de la ville (par exemple, \"Paris\", \"Tokyo\").\n" + //
                " check_in_date (str) : date d'arrivée au format AAAA-MM-JJ.\n" + //
                " Renvoie :\n" + //
                " dict : résultats de la recherche d'hôtels.\n" + //
                " En cas de succès : {'status': 'success', 'hotels': [...], 'count': N}\n" + //
                " En cas d'erreur : {'status': 'error', 'error_message': 'explanation'}")
    public Map<String,Object> searchHotels(
        @Schema(name = "city", description = "Le nom de la ville (par exemple, \"Paris\", \"Tokyo\").") String city,
        @Schema(name = "check_in_date", description = "Date d'arrivée au format AAAA-MM-JJ.") String check_in_date
    ){
        HashMap<String,List<HashMap<String, Object>>> availableHotels = new HashMap<>();
        availableHotels.put("paris", List.of(
            new HashMap<>(){{
                put("name", "Hotel Eiffel");
                put("price_per_night_usd", 150);
                put("rating", 4.5);
            }},
            new HashMap<>(){{
                put("name", "Louvre Inn");
                put("price_per_night_usd", 120);
                put("rating", 4.2);
            }}
        ));
        availableHotels.put("tokyo", List.of(
            new HashMap<>(){{
                put("name", "Shibuya Grand");
                put("price_per_night_usd", 180);
                put("rating", 4.7);
            }},
            new HashMap<>(){{
                put("name", "Tokyo Bay Hotel");
                put("price_per_night_usd", 140);
                put("rating", 4.3);
            }}
        ));
        String cityKey = city.toLowerCase();
        if (!availableHotels.containsKey(cityKey)) {
            return Map.of(
                "status", "error",
                "error_message", "Aucun hôtel trouvé pour " + city + ". Essayez Paris ou Tokyo."
            );
        }
        return Map.of(
            "status", "success",
            "city", city,
            "check_in_date", check_in_date,
            "hotels", availableHotels.get(cityKey),
            "count", availableHotels.get(cityKey).size()
        );
    }

    @Schema(name = "calculateTripBudget", description = "\"Calcule le budget total du voyage, vols et hébergement compris.\n" + //
                " Utilise ceci après avoir trouvé les prix des vols et des hôtels pour donner au\n" + //
                "client une estimation totale.\n" + //
                " Args:\n" + //
                " flight_price (float) : coût du vol aller-retour en USD.\n" + //
                " hotel_price (float) : coût de l'hôtel par nuit en USD.\n" + //
                " num_nights (int) : nombre de nuits sur place.\n" + //
                " Renvoie :\n" + //
                " dict : répartition du budget.\n" + //
                " Renvoie toujours : {'status': 'success', 'total_usd': X, 'breakdown':\n" + //
                "{...}}")
    public Map<String,Object> calculateTripBudget(
        @Schema(name = "flight_price", description = "Coût du vol aller-retour en USD.") float flight_price,
        @Schema(name = "hotel_price", description = "Coût de l'hôtel par nuit en USD.") float hotel_price,
        @Schema(name = "num_nights", description = "Nombre de nuits sur place.") int num_nights
    ){
        float totalHotel = hotel_price * num_nights;
        float total = flight_price + totalHotel;
        return Map.of(
            "status", "success",
            "total_usd", String.format("%.2f", total),
            "breakdown", Map.of(
                "flight_cost",flight_price,
                "hotel_cost_per_night", hotel_price,
                "num_nights", num_nights,
                "hotel_total", String.format("%.2f", totalHotel)
            )
        );
    }
}
