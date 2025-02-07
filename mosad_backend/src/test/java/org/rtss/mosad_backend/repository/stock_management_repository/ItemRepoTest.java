package org.rtss.mosad_backend.repository.stock_management_repository;

import org.junit.jupiter.api.Test;
import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.rtss.mosad_backend.entity.stock_management_entity.Brand;
import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemBranch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepoTest {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Test
    void shouldFindItemsByCategoryAndBrand() {
        // Arrange
        Category category = new Category();
        category.setCategoryName("Electronics");

        categoryRepo.save(category);

        Brand brand = new Brand();
        brand.setBrandName("Sony");

        brandRepo.save(brand);


        Item item = new Item();
        item.setItemName("TV");
        item.setItemDescription("A TV");
        item.setRetailPrice(1000);
        item.setCompanyPrice(800);
        item.setDiscount(10);
        item.setCategory(category);
        item.setBrand(brand);

        itemRepo.save(item);

        ItemBranch itemBranch = new ItemBranch();
        itemBranch.setAvailableQuantity(10);
        itemBranch.setItem(item);
        itemBranch.setBranch(new Branch());

        // Act
        List<Item> items = itemRepo.findByCategoryAndBrand(category, brand);

        // Assert
        assertNotNull(items);
        assertFalse(items.isEmpty());
        assertEquals("TV", items.get(0).getItemName());
    }

}