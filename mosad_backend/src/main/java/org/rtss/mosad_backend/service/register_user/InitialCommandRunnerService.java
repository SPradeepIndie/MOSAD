package org.rtss.mosad_backend.service.register_user;

import org.rtss.mosad_backend.config.security.PasswordEncoder;
import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.exceptions.DbTableInitException;
import org.rtss.mosad_backend.repository.branch_management.BranchRepo;
import org.rtss.mosad_backend.repository.user_management.UserRolesRepo;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Component
public class InitialCommandRunnerService implements CommandLineRunner {

    public static final String MIRIGAMA_BRANCH = "Mirigama Branch";
    private final PasswordEncoder passwordEncoder;
    private final UsersRepo usersRepo;
    private final UserRolesRepo userRolesRepo;
    private final JdbcTemplate jdbcTemplate;
    private final BranchRepo branchRepo;

    public InitialCommandRunnerService(PasswordEncoder passwordEncoder, UsersRepo usersRepo, UserRolesRepo userRolesRepo, JdbcTemplate jdbcTemplate,BranchRepo branchRepo) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepo = usersRepo;
        this.userRolesRepo = userRolesRepo;
        this.jdbcTemplate = jdbcTemplate;
        this.branchRepo = branchRepo;
    }

    @Override
    public void run(String... args) throws DbTableInitException {
        // Execute the SQL script to initialize default roles
        initializeDatabaseBranch();
        initializeDatabaseRoles();
        addInitialAdminUser();
    }

    private void addInitialAdminUser() {
        if (usersRepo.findByUsername("admin").isEmpty()) {
            UserRoles userRoles=new UserRoles();
            Users admin=new Users();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.bCryptPasswordEncoder().encode("admin123"));
            admin.setFirstName("Admin");
            admin.setUserRoles(userRolesRepo.findUserRolesByRoleName("ADMIN")
                    .orElseGet(() -> {
                        System.out.println("No roles found for role name 'ADMIN'");
                        userRoles.setRoleName("ADMIN");
                        return userRoles;
                    }));
            Optional<Branch> initialBranches=branchRepo.findBranchByBranchName(MIRIGAMA_BRANCH);
            if(initialBranches.isEmpty()){
               throw new DbTableInitException("Mirigama Branch not found");
            }
            Branch initialBranch=initialBranches.get();
            Set<Users> newUsers=new HashSet<>();
            newUsers.add(admin);
            initialBranch.setUsers(newUsers);
            admin.setBranch(initialBranch);

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
                SELECT 'BRANCH_MANAGER'
                    WHERE NOT EXISTS (SELECT 1 FROM public.user_roles WHERE role_name = 'BRANCH_MANAGER')
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

    //Initialize the default branch
    private void initializeDatabaseBranch() {
        if(branchRepo.findBranchByBranchName(MIRIGAMA_BRANCH).isEmpty()){
            Branch branch=new Branch();
            branch.setBranchName(MIRIGAMA_BRANCH);
            branch.setAddressNumber("205/106");
            branch.setStreetName("Pattiyakuburawatta");
            branch.setCity("Hakurukubura, Mirigama");
            branchRepo.save(branch);
        }
    }
}