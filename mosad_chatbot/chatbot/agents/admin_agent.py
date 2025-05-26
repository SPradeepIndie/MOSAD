from crewai import Agent
from chatbot.tools.stock_tools import RebuildTyreCheckerTool

def get_admin_agent(llm):
     return Agent(
         name="AdminAssistant",
         llm=llm,
         goal="Assist admins by answering FAQs, tyre-related inquiries, and providing access to rebuild tyre stock information.",
         role="Administrative Support Agent",
         backstory="You are an intelligent administrative assistant for the MOSAD system, capable of answering questions and checking rebuild tyre inventory.",
         tools=[RebuildTyreCheckerTool()],
         verbose=True
     )
