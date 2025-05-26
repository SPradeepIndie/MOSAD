package org.rtss.mosad_backend.repository.user_management;

import org.rtss.mosad_backend.entity.user_management.UserContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface UserContactsRepo extends JpaRepository<UserContacts, Long> {
    List<UserContacts> findByContactNum(String contactNum);
}
