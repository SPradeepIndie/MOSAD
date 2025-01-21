package org.rtss.mosad_backend.repository.stock_management_repository;

import org.junit.jupiter.api.Test;
import org.rtss.mosad_backend.entity.stock_management_entity.Brand;
import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BrandRepoTest {

    @Autowired
    private BrandRepo brandRepo;

    @Test
    void shouldFindBrandByBrandName() {
        //given
        Brand brand=new Brand();
        brand.setBrandName("brand1");
        brand.setCategories(new HashSet<>(List.of(new Category("cat1"))));
        brandRepo.save(brand);

        //When
        Optional<Brand> brands=brandRepo.findByBrandName("brand1");

        //Then
        assertTrue(brands.isPresent());
        assertEquals("brand1", brands.get().getBrandName());
    }

    @Test
    void shouldNotGiveBrandForNotExistingBrandName() {
        Optional<Brand> brands=brandRepo.findByBrandName("brand3");

        assertFalse(brands.isPresent());
    }

}