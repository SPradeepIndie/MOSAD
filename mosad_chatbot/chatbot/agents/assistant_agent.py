from crewai import Agent

def get_assistant_agent(llm, faq_context):
    return Agent(
        name="HelperBot",
        llm=llm,
        goal="Assist users by providing accurate answers based on the MOSAD FAQ, system or query data.",
        role="Support Assistant",  # Required field: defines the agent's purpose
        backstory="I am HelperBot, created to help users navigate and understand the MOSAD system using the provided FAQ and context.",  # Required field: provides context about the agent
        verbose=True  # Optional: for debugging
    )
