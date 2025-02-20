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
            admin.setPassword(passwordEncoder.bCryptPasswordEncoder().encode("admin123")); // Replace with a strong default password
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
        try {
            jdbcTemplate.execute(
                    "DO $$BEGIN\n" +
                            "    IF EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'user_roles') THEN\n" +
                            "        INSERT INTO public.user_roles(role_name)\n" +
                            "        SELECT 'ADMIN'\n" +
                            "            WHERE NOT EXISTS (SELECT 1 FROM public.user_roles WHERE role_name = 'ADMIN')\n" +
                            "        UNION ALL\n" +
                            "        SELECT 'OWNER'\n" +
                            "            WHERE NOT EXISTS (SELECT 1 FROM public.user_roles WHERE role_name = 'OWNER')\n" +
                            "        UNION ALL\n" +
                            "        SELECT 'STOCK_MANAGER'\n" +
                            "            WHERE NOT EXISTS (SELECT 1 FROM public.user_roles WHERE role_name = 'STOCK_MANAGER')\n" +
                            "        UNION ALL\n" +
                            "        SELECT 'RETAIL_CUSTOMER'\n" +
                            "            WHERE NOT EXISTS (SELECT 1 FROM public.user_roles WHERE role_name = 'RETAIL_CUSTOMER')\n" +
                            "        UNION ALL\n" +
                            "        SELECT 'MECHANIC'\n" +
                            "            WHERE NOT EXISTS (SELECT 1 FROM public.user_roles WHERE role_name = 'MECHANIC');\n" +
                            "    END IF;\n" +
                            "END $$;"
            );
        } catch (Exception e) {
            throw new DbTableInitException("Failed to initialize default user roles"+e.getMessage());
        }
    }
}