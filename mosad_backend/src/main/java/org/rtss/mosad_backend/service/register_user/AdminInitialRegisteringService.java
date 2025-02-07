package org.rtss.mosad_backend.service.register_user;

import org.rtss.mosad_backend.config.security.PasswordEncoder;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.exceptions.DbTableInitException;
import org.rtss.mosad_backend.repository.user_management.UserRolesRepo;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class AdminInitialRegisteringService implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepo usersRepo;
    private final UserRolesRepo userRolesRepo;

    public AdminInitialRegisteringService(PasswordEncoder passwordEncoder, UsersRepo usersRepo, UserRolesRepo userRolesRepo) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepo = usersRepo;
        this.userRolesRepo = userRolesRepo;
    }

    @Override
    public void run(String... args) throws DbTableInitException {
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
}