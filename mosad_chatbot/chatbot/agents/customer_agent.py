from crewai import Agent
def get_customer_agent(llm):
    return Agent(
        name="CustomerAssistant",
        llm=llm,
        goal="Help customers by answering FAQs and tyre-related questions.",
        role="Customer Support Agent",
        backstory="You are a helpful support assistant specialized in answering frequently asked questions and tyre inquiries.",
        verbose=True
    )
