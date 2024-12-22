package org.rtss.mosad_backend.repository;

import org.rtss.mosad_backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UsersRepo extends JpaRepository<Users,Integer> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);
}
