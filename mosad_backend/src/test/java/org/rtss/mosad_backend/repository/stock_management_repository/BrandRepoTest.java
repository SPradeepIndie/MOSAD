package org.rtss.mosad_backend.repository.stock_management_repository;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.rtss.mosad_backend.entity.stock_management_entity.Brand;
import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BrandRepoTest {

    @Autowired
    private BrandRepo brandRepo;

    @Test
    @Disabled
    void findByBrandName() {
        //given
        Brand brand=new Brand();
        brand.setBrandName("brand1");
        brand.setCategories(new HashSet<>(List.of(new Category("cat1"))));
        brandRepo.save(brand);

        brand.setBrandName("brand2");
        brand.setCategories(new HashSet<>(List.of(new Category("category"))));
        brandRepo.save(brand);

        brand.setBrandName("brand3");
        brand.setCategories(new HashSet<>(List.of(new Category("cat1"))));
        brandRepo.save(brand);

    }

}