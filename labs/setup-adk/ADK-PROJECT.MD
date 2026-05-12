```bash
export PATH=$PATH:"/home/${USER}/.local/bin"
python3 -m pip install google-adk

gcloud auth application-default login


gcloud storage cp gs://qwiklabs-gcp-00-33ae6c6d92c1-bucket/adk_project.zip .
unzip adk_project.zip
cd adk_project
pip install -r requirements.txt
```

# variable env
```
GOOGLE_GENAI_USE_VERTEXAI=true
GOOGLE_CLOUD_PROJECT=qwiklabs-gcp-00-33ae6c6d92c1
GOOGLE_CLOUD_LOCATION=global
MODEL=gemini-2.5-flash
```

# GOOGLE SERACH EXAMPLE
```text
from google.adk import Agent
from google.adk.tools import google_search  # The Google Search tool

import os
import sys
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))
from callback_logging import log_query_to_model, log_model_response


root_agent = Agent(
    # name: A unique name for the agent.
    name="google_search_agent",
    # description: A short description of the agent's purpose, so
    # other agents in a multi-agent system know when to call it.
    description="Answer questions using Google Search.",
    # model: The LLM model that the agent will use:
    model=os.getenv("MODEL"),
    # instruction: Instructions (or the prompt) for the agent.
    instruction="You are an expert researcher. You stick to the facts.",
    # callbacks: Allow for you to run functions at certain points in
    # the agent's execution cycle. In this example, you will log the
    # request to the agent and its response.
    before_model_callback=log_query_to_model,
    after_model_callback=log_model_response,
    # tools: functions to enhance the model's capabilities.
    # Add the google_search tool below.
    tools = [google_search]
)
```


## Run web
```bash
cd ~/adk_project
adk web --allow_origins "regex:https://.*\.cloudshell\.dev"
```

## Run CLI
```bash
adk run my_google_search_agent
```

# AGENT GEO VALIDATEUR
 Rendre le format de retour du model en JSON 

```text
import asyncio
from google.adk import Agent
from google.adk.runners import InMemoryRunner
from google.adk.sessions import Session
from google.genai import types
from pydantic import BaseModel

import os
from dotenv import load_dotenv

import sys
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))
from callback_logging import log_query_to_model, log_model_response
import google.cloud.logging

# 1. Load environment variables from the agent directory's .env file
load_dotenv()
google_cloud_project = os.getenv("GOOGLE_CLOUD_PROJECT")
google_cloud_location = os.getenv("GOOGLE_CLOUD_LOCATION")
google_genai_use_vertexai = os.getenv("GOOGLE_GENAI_USE_VERTEXAI")
model_name = os.getenv("MODEL")

cloud_logging_client = google.cloud.logging.Client()
cloud_logging_client.setup_logging()

class CountryCapital(BaseModel):
    capital : str

# Create an async main function
async def main():

    # 2. Set or load other variables
    app_name = 'geo_validator_app'
    user_id_1 = 'user1'

    # 3. Define Your Agent
    root_agent = Agent(
        model=model_name,
        name="geo_validator",
        instruction="Answer questions.",
        before_model_callback=log_query_to_model,
        after_model_callback=log_model_response,
        output_schema = CountryCapital.model_json_schema(),
        disallow_transfer_to_parent=True,
        disallow_transfer_to_peers=True
    )

    # 3. Create a Runner
    runner = InMemoryRunner(
        agent=root_agent,
        app_name=app_name,
    )

    # 4. Create a session
    my_session = await runner.session_service.create_session(
        app_name=app_name, user_id=user_id_1
    )

    # 5. Prepare a function to package a user's message as
    # genai.types.Content, run it asynchronously, and iterate
    # through the response 
    async def run_prompt(session: Session, new_message: str):
        content = types.Content(
                role='user', parts=[types.Part.from_text(text=new_message)]
            )
        print('** User says:', content.model_dump(exclude_none=True))
        async for event in runner.run_async(
            user_id=user_id_1,
            session_id=session.id,
            new_message=content,
        ):
            if event.content.parts and event.content.parts[0].text:
                print(f'** {event.author}: {event.content.parts[0].text}')

        cloud_logging_client.close()


    # 6. Use this function on a new query
    query = "What is the capital of France?"
    await run_prompt(my_session, query)

if __name__ == "__main__":
    asyncio.run(main())

```
## REUN VERIFICATION
```
python3 geo_validator/agent.py
```

# Déployer l'auditeur de brochures : MULTI - AGENT

AGENT SEQUENTIEL

```code
from google.adk.agents import SequentialAgent

from .sub_agents.critic import critic_agent
from .sub_agents.reviser import reviser_agent  # <--- TODO: ENABLE THIS

import logging
import google.cloud.logging

logging.basicConfig()

cloud_logging_client = google.cloud.logging.Client()
cloud_logging_client.setup_logging()


llm_auditor = SequentialAgent(
    name='llm_auditor',
    description=(
        'Evaluates LLM-generated answers, verifies actual accuracy using the'
        ' web, and refines the response to ensure alignment with real-world'
        ' knowledge.'
    ),
    sub_agents=[critic_agent, reviser_agent], # <--- TODO: ADD reviser_agent CHECK HERE
)

root_agent = llm_auditor
```
