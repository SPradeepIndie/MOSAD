-- Insert ADMIN,OWNER,STOCK_MANAGER,RETAIL_CUSTOMER,MECHANIC admin roles in to
-- user_roles if they are not exists in table
-- This will run at the initializing step in spring boot application

INSERT INTO public.user_roles(role_name)
SELECT 'ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM public.user_roles WHERE role_name = 'ADMIN')
UNION ALL
SELECT 'OWNER'
    WHERE NOT EXISTS (SELECT 1 FROM public.user_roles WHERE role_name = 'OWNER')
UNION ALL
SELECT 'STOCK_MANAGER'
    WHERE NOT EXISTS (SELECT 1 FROM public.user_roles WHERE role_name = 'STOCK_MANAGER')
UNION ALL
SELECT 'RETAIL_CUSTOMER'
    WHERE NOT EXISTS (SELECT 1 FROM public.user_roles WHERE role_name = 'RETAIL_CUSTOMER')
UNION ALL
SELECT 'MECHANIC'
    WHERE NOT EXISTS (SELECT 1 FROM public.user_roles WHERE role_name = 'MECHANIC');


