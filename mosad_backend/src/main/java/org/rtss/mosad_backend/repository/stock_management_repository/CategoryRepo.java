package org.rtss.mosad_backend.repository.stock_management_repository;

import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface CategoryRepo extends JpaRepository<Category, Long> {
    static Optional<Category> findCategoryByCategoryName(String name) {
        return null;
    }
}
