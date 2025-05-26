from crewai.tools.base_tool import BaseTool
import psycopg2
import os

class RebuildTyreCheckerTool(BaseTool):
    name: str = "RebuildTyreCheckerTool"
    description: str = "Check the rebuild tyre customer names from the MOSAD system database."

    def _run(self, query: str) -> str:
     try:
        # Connect to PostgreSQL database
        conn = psycopg2.connect(
            host=os.getenv("DB_HOST", "localhost"),
            database=os.getenv("DB_NAME", "mosad"),
            user=os.getenv("DB_USER", "postgres"),
            password=os.getenv("DB_PASSWORD", "db_password"),
            port=os.getenv("DB_PORT", "5432")
        )
        cur = conn.cursor()

        cur.execute("SELECT customer_name FROM public.rebuild_tyre")
        rows = cur.fetchall()

        if not rows:
          return "No rebuild tyre customer data available."

         # ✅ Build customer names list
        result = "🧾 Rebuild Tyre Customers:\n"
        for (customer_name,) in rows:   # NOTICE: Only 1 field per row!
            result += f"- {customer_name}\n"

        cur.close()
        conn.close()
        return result
     except Exception as e:
            print(f"Admin Tool Error: {e}")
            return "❌ Failed to check rebuild tyre stock. Please try again later."