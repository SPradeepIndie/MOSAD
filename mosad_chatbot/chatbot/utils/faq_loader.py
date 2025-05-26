import pandas as pd

def load_faq_dataframe(path: str) -> pd.DataFrame:
    faq_df = pd.read_csv(path)
    faq_df.columns = ["Question", "Answer"] 
    return faq_df
