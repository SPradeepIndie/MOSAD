package org.rtss.mosad_backend.service.register_user;

import org.rtss.mosad_backend.config.security.PasswordEncoder;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.exceptions.DbTableInitException;
import org.rtss.mosad_backend.repository.user_management.UserRolesRepo;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class InitialCommandRunnerService implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepo usersRepo;
    private final UserRolesRepo userRolesRepo;
    private final JdbcTemplate jdbcTemplate;

    public InitialCommandRunnerService(PasswordEncoder passwordEncoder, UsersRepo usersRepo, UserRolesRepo userRolesRepo, JdbcTemplate jdbcTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepo = usersRepo;
        this.userRolesRepo = userRolesRepo;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws DbTableInitException {
        // Execute the SQL script to initialize default roles
        initializeDatabaseRoles();
        addInitialAdminUser();
    }

    private void addInitialAdminUser() {
        if (usersRepo.findByUsername("admin").isEmpty()) {
            UserRoles userRoles=new UserRoles();
            Users admin=new Users();
            admin.setUsername("admin");
            // **Crucial:** Encrypt the password before saving
            admin.setPassword(passwordEncoder.bCryptPasswordEncoder().encode("admin123"));
            admin.setFirstName("Admin");
            admin.setUserRoles(userRolesRepo.findUserRolesByRoleName("ADMIN")
                    .orElseGet(() -> {
                        System.out.println("No roles found for role name 'ADMIN'");
                        userRoles.setRoleName("ADMIN");
                        return userRoles;
                    }));
            usersRepo.save(admin);
        }
    }

    private void initializeDatabaseRoles() {
        String sql = """
        DO $$
        BEGIN
            IF EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'user_roles') THEN
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
            END IF;
        END $$;
        """;
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw new DbTableInitException("Failed to initialize default user roles"+e.getMessage());
        }
    }
}