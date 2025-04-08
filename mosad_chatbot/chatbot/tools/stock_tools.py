// stock_tools.py (Upgraded Full Version)

from crewai.tools.base_tool import BaseTool
import psycopg2
import os

class RebuildTyreCheckerTool(BaseTool):
    name: str = "RebuildTyreCheckerTool"
    description: str = "Check detailed rebuild tyre records from the MOSAD system, including customer names, tyre size, brand, status, price, and important dates."

    def _run(self, query: str) -> str:
        try:
            conn = psycopg2.connect(
                host=os.getenv("DB_HOST", "localhost"),
                database=os.getenv("DB_NAME", "mosad"),
                user=os.getenv("DB_USER", "postgres"),
                password=os.getenv("DB_PASSWORD", "your_db_password"),
                port=os.getenv("DB_PORT", "5432")
            )
            cur = conn.cursor()

            # ✅ Select all fields
            cur.execute("SELECT * FROM public.rebuild_tyre")
            columns = [desc[0] for desc in cur.description]  # Get column names
            rows = cur.fetchall()

            if not rows:
                return "No rebuild tyre data available."

            # ✅ Build clean table output
            result = "🧾 Rebuild Tyre Records:\n"
            for row in rows:
                record = ", ".join(f"{col}: {val}" for col, val in zip(columns, row))
                result += f"- {record}\n"

            cur.close()
            conn.close()
            return result

        except Exception as e:
            print(f"Admin Tool Error: {e}")
            return "❌ Failed to check rebuild tyre records. Please try again later."
