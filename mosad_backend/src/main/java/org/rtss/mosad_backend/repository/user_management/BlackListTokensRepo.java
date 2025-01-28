package org.rtss.mosad_backend.repository.user_management;

import org.rtss.mosad_backend.entity.user_management.BlackListTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface BlackListTokensRepo extends JpaRepository<BlackListTokens,Long> {
    Optional<BlackListTokens> findByToken(String token);
}
