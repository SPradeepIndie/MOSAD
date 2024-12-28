package org.rtss.mosad_backend.repository.user_management;

import org.rtss.mosad_backend.entity.user_management.UserContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserContactsRepo extends JpaRepository<UserContacts, String> {
}
