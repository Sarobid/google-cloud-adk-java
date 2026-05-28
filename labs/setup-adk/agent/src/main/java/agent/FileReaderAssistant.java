package agent;

import java.util.List;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.mcp.McpToolset;
import com.google.adk.tools.mcp.StdioServerParameters;

public class FileReaderAssistant {
    public static BaseAgent ROOT_AGENT = new FileReaderAssistant().getAgent();
    private BaseAgent agent = null;
    
    public BaseAgent getAgent() {
        return this.agent;
    }

    public FileReaderAssistant(){
        this.initAgent();
    }

    private void initAgent() {
        this.agent = LlmAgent.builder()
        .name("file_reader_assistant")
        .description("'Aide les utilisateurs à lire et à explorer des fichiers à l'aide des\n" + //
                        "outils MCP")
        .instruction("""
            Tu es un assistant de lecture de fichiers qui aide les utilisateurs à explorer des
                fichiers.
            Tes capacités :
            - Lister les fichiers dans les répertoires à l'aide de list_directory
            - Lire le contenu des fichiers à l'aide de read_file
            Lorsque tu aides les utilisateurs, procède comme suit :
            1. Utilise list_directory pour afficher les fichiers disponibles.
            2. Utilise read_file pour afficher le contenu d'un fichier lorsqu'on te le
                demande.
            3. Décris ce que tu trouves de manière utile.
            Veille toujours à bien identifier le dossier sur lequel tu travailles.
            """
        )
        .model("gemini-2.5-flash")
        .tools(this.createMcpToolset())
        .build();
    } 
    
    private McpToolset createMcpToolset() {
        String allowedPath = "/home/odoo-6/agentdev/agent-developpement-kit/learning/google-cloud-adk-lab/labs/setup-adk/my_files";

        StdioServerParameters stdioServerParameters =  StdioServerParameters.builder()
                .command("npx")
                .args(List.of(
                        "-y",
                        "@modelcontextprotocol/server-filesystem",
                        allowedPath
                ))
                .build();
        McpToolset toolset = new McpToolset(stdioServerParameters.toServerParameters());
      
        return toolset;
    }
}
