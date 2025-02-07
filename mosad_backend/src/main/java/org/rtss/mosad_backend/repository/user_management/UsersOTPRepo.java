package org.rtss.mosad_backend.repository.user_management;

import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.entity.user_management.UsersOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UsersOTPRepo extends JpaRepository<UsersOTP, Long> {

    Optional<UsersOTP> findByOtpTokenAndUser(String otpToken, Users user);
}
