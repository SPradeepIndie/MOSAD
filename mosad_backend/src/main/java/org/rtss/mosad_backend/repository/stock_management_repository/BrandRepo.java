package org.rtss.mosad_backend.repository.stock_management_repository;

import org.rtss.mosad_backend.entity.stock_management_entity.Brand;
import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@EnableJpaRepositories
public interface BrandRepo extends JpaRepository<Brand, Long> {
    Optional<Brand> findByBrandName(String brandName);

//    @Query(
//            "SELECT * from public."
//    )
//    Set<Brand> findBrandsByCategoryName(Category category);
}
