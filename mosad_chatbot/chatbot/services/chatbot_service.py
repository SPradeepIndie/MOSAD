from langchain_openai import ChatOpenAI
from chatbot.config import OPENAI_API_KEY, OPENAI_MODEL_NAME
from chatbot.utils.faq_loader import load_faq_dataframe
from chatbot.agents.customer_agent import get_customer_agent
from chatbot.agents.admin_agent import get_admin_agent
from crewai import Task
from rapidfuzz import fuzz

llm = ChatOpenAI(openai_api_key=OPENAI_API_KEY, model_name=OPENAI_MODEL_NAME)
faq_df = load_faq_dataframe("faq/mosad_faq.csv")

# Initialize agents once
customer_agent = get_customer_agent(llm)
admin_agent = get_admin_agent(llm)

def get_top_faq_matches(user_question: str, top_n: int = 3) -> str:
    scored = [
        (fuzz.token_sort_ratio(user_question, q), q, a)
        for q, a in zip(faq_df["Question"], faq_df["Answer"])
    ]
    top_matches = sorted(scored, reverse=True)[:top_n]
    return "\n".join([f"Q: {q}\nA: {a}" for _, q, a in top_matches])

def handle_chat(message: str, role: str = "customer") -> str:
    # Pick agent based on role
    agent = admin_agent if role == "admin" else customer_agent

    # If Admin, allow tool usage; else, FAQ match only
    if role == "admin":
        relevant_context = "Admin access granted. You can check rebuild tyre stock or ask FAQ questions."
    else:
        relevant_context = get_top_faq_matches(message)

    task_description = (
        f"You are a helpful assistant. Use the following context to answer the user question:\n\n"
        f"{relevant_context}\n\n"
        f"User question: {message}"
    )

    task = Task(
        description=task_description,
        agent=agent,
        expected_output="A clear and accurate answer based on either the FAQ or the rebuild tyre stock data."
    )
    return agent.execute_task(task)
