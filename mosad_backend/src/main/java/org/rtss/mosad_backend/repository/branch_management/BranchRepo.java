package org.rtss.mosad_backend.repository.branch_management;

import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface BranchRepo extends JpaRepository<Branch, Long> {
}
