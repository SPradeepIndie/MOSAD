package org.rtss.mosad_backend.service;

import org.rtss.mosad_backend.dto.ItemDataDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.rtss.mosad_backend.repository.stock_management_repository.BrandRepo;
import org.rtss.mosad_backend.repository.stock_management_repository.CategoryRepo;
import org.rtss.mosad_backend.repository.stock_management_repository.ItemRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServices {
    private final CategoryRepo categoryRepo;
    private final BrandRepo brandRepo;
    private final ItemRepo itemRepo;

    public ReportServices(CategoryRepo categoryRepo, BrandRepo brandRepo, ItemRepo itemRepo) {
        this.categoryRepo = categoryRepo;
        this.brandRepo = brandRepo;
        this.itemRepo = itemRepo;
    }

    public List<ItemDataDTO> getCategoryCounts() {
        List<Category> categories = categoryRepo.findAll();
        List<ItemDataDTO> itemDataDTOList = new ArrayList<>();

        for (Category category : categories) {
            int brandCount = category.getBrands() != null ? category.getBrands().size() : 0;
            int itemCount = category.getItems() != null ? category.getItems().size() : 0;
            itemDataDTOList.add(new ItemDataDTO(category.getCategoryName(), brandCount, itemCount));
        }

        return itemDataDTOList;
    }
}
