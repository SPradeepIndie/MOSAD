package org.rtss.mosad_backend.service.stock_management_service;
import org.rtss.mosad_backend.repository.stock_management_repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDeleteService {
   
    private final ItemBatteryRepo itemBatteryRepository;

    private final ItemTubeRepo itemTubeRepository;

    
    private final ItemTyreAtlanderRepo itemTyreAtlanderRepository;

    
    private final ItemTyreLinglongRepo itemTyreLinglongRepository;

    
    private final ItemTyrePresaRepo itemTyrePresaRepository;

    
    private final ItemTyreRapidRepo itemTyreRapidRepository;

    
    private final ItemOtherAccessoriesRepo itemOtherAccessoriesRepository;

    // Method to delete item
    public boolean deleteItem(String category, String brand, Integer id) {
        switch (category.toLowerCase()) {
            case "battery":
                itemBatteryRepository.deleteById(id);
                break;
            case "tube":
                itemTubeRepository.deleteById(id);
                break;
            case "tyre":
                deleteTyreByBrand(brand, id);
                break;
            case "other":
                itemOtherAccessoriesRepository.deleteById(id);
                break;
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
        return false;
    }

    // Helper method to delete a tyre by brand
    private void deleteTyreByBrand(String brand, Integer id) {
        if (brand == null) {
            throw new IllegalArgumentException("Brand must be specified for Tyre category.");
        }
        switch (brand.toLowerCase()) {
            case "atlander":
                itemTyreAtlanderRepository.deleteById(id);
                break;
            case "linglong":
                itemTyreLinglongRepository.deleteById(id);
                break;
            case "presa":
                itemTyrePresaRepository.deleteById(id);
                break;
            case "rapid":
                itemTyreRapidRepository.deleteById(id);
                break;
            default:
                throw new IllegalArgumentException("Invalid brand: " + brand);
        }
    }
}
