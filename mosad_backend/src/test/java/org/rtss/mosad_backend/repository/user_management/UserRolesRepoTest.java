package org.rtss.mosad_backend.repository.user_management;

import org.junit.jupiter.api.Test;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRolesRepoTest {

    @Autowired
    private UserRolesRepo userRolesRepo;

    @Test
    void shouldGiveUserRolesByRoleName() {
        //given
        UserRoles userRole=new UserRoles();
        String roleName = "ADMIN";
        userRole.setRoleName(roleName);
        userRolesRepo.save(userRole);

        //when
        Optional<UserRoles> foundRole=userRolesRepo.findUserRolesByRoleName(roleName);

        //then
        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getRoleName()).isEqualTo(roleName);
    }
    @Test
    public void shouldNotGiveUserRoleForNonExistentRoleName() {
        // When
        Optional<UserRoles> foundRole = userRolesRepo.findUserRolesByRoleName("NON_EXISTENT_ROLE");

        // Then
        assertThat(foundRole).isNotPresent();
    }

}