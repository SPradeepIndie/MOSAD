package org.rtss.mosad_backend.service.stock_management_service;

import org.rtss.mosad_backend.repository.stock_management_repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





import java.util.List;

@Service
public class ItemViewService {

    @Autowired
    private ItemBatteryRepo itemBatteryRepository;

    @Autowired
    private ItemTubeRepo itemTubeRepository;

    @Autowired
    private ItemTyreAtlanderRepo itemTyreAtlanderRepository;

    @Autowired
    private ItemTyreLinglongRepo itemTyreLinglongRepository;

    @Autowired
    private ItemTyrePresaRepo itemTyrePresaRepository;

    @Autowired
    private ItemTyreRapidRepo itemTyreRapidRepository;

    @Autowired
    private ItemOtherAccessoriesRepo itemOtherAccessoriesRepository;


        public void setItemBatteryRepository(ItemBatteryRepo repo) {
            this.itemBatteryRepository = repo;
        }

        public void setItemTubeRepository(ItemTubeRepo repo) {
            this.itemTubeRepository = repo;
        }

        public void setItemTyreAtlanderRepository(ItemTyreAtlanderRepo repo) {
            this.itemTyreAtlanderRepository = repo;
        }

        public void setItemTyreLinglongRepository(ItemTyreLinglongRepo repo) {
            this.itemTyreLinglongRepository = repo;
        }

        public void setItemTyrePresaRepository(ItemTyrePresaRepo repo) {
            this.itemTyrePresaRepository = repo;
        }

        public void setItemTyreRapidRepository(ItemTyreRapidRepo repo) {
            this.itemTyreRapidRepository = repo;
        }

        public void setItemOtherAccessoriesRepository(ItemOtherAccessoriesRepo repo) {
            this.itemOtherAccessoriesRepository = repo;
        }




    // Category Selection
    public List<?> getItems(String category, String brand) {
        switch (category.toLowerCase()) {
            case "battery":
                return itemBatteryRepository.findAll();
            case "tube":
                return itemTubeRepository.findAll();
            case "tyre":
                return fetchTyreByBrand(brand);
            case "other":
                return itemOtherAccessoriesRepository.findAll();
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
    }


//Item Brand (Tyre only) Selection
    private List<?> fetchTyreByBrand(String brand) {
        if (brand == null) {
            throw new IllegalArgumentException("Brand must be specified for Tyre category.");
        }
        switch (brand.toLowerCase()) {
            case "atlander":
                return itemTyreAtlanderRepository.findAll();
            case "linglong":
                return itemTyreLinglongRepository.findAll();
            case "presa":
                return itemTyrePresaRepository.findAll();
            case "rapid":
                return itemTyreRapidRepository.findAll();
            default:
                throw new IllegalArgumentException("Invalid brand: " + brand);
        }
    }
}
