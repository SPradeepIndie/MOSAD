package org.rtss.mosad_backend.repository.user_management;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsersRepoTest {

    @Autowired
    private UserRolesRepo userRolesRepo;

    @Autowired
    private UsersRepo usersRepo;
    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setPassword("test");
        user.setFirstName("testFirstName");

        //Create UserRole ADMIN in db for testing
        UserRoles userRole=new UserRoles();
        String roleName = "ADMIN";
        userRole.setRoleName(roleName);
        userRolesRepo.save(userRole);

        user.setUserRoles(userRole);
    }

    @AfterEach
    void tearDown() {
        usersRepo.deleteAll();
        userRolesRepo.deleteAll();
    }

    @Test
    void shouldGiveUserByUsername() {
        //given
        String username = "John";
        user.setUsername(username);
        usersRepo.save(user);

        //when
        Optional<Users> foundUser=usersRepo.findByUsername(username);

        //then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(username);
    }

    @Test
    public void shouldNotGiveUserForNonExistentUsername() {
        // When
        Optional<Users> foundUser = usersRepo.findByUsername("nonUsername");

        // Then
        assertThat(foundUser).isNotPresent();
    }

    @Test
    void shouldGiveUserByEmail() {
        //given
        String username = "John";
        user.setUsername(username);
        String email = "John@gamil.com";
        user.setEmail(email);
        usersRepo.save(user);

        //when
        Optional<Users> foundUser=usersRepo.findByEmail(email);

        //then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(email);
    }
    @Test
    public void shouldNotGiveUserForNonExistentEmail() {
        // When
        Optional<Users> foundUser = usersRepo.findByEmail("nonUser@gamil.com");

        // Then
        assertThat(foundUser).isNotPresent();
    }
}