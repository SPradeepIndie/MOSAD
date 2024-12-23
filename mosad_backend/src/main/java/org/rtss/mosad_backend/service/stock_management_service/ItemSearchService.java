package org.rtss.mosad_backend.service.stock_management_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemSearchService {

    private final ItemTyreAtlanderService atlanderService;
    private final ItemTyreLinglongService linglongService;
    private final ItemTyrePresaService presaService;
    private final ItemTyreRapidService rapidService;

    @Autowired
    public ItemSearchService(
            ItemTyreAtlanderService atlanderService,
            ItemTyreLinglongService linglongService,
            ItemTyrePresaService presaService,
            ItemTyreRapidService rapidService) {
        this.atlanderService = atlanderService;
        this.linglongService = linglongService;
        this.presaService = presaService;
        this.rapidService = rapidService;
    }

    public List<?> searchByBrandAndSize(String brand, String size) {
        switch (brand.toLowerCase()) {
            case "atlander":
                return atlanderService.searchBySize(size);
            case "linglong":
                return linglongService.searchBySize(size);
            case "prasa":
                return presaService.searchBySize(size);
            case "rapid":
                return rapidService.searchBySize(size);
            default:
                throw new IllegalArgumentException("Invalid brand name: " + brand);
        }
    }
}
