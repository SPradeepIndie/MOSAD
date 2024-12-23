package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyreRapidDTO;
import org.rtss.mosad_backend.service.stock_management_service.ItemTyreRapidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/stock/tyre_rapid")
@CrossOrigin

public class ItemTyreRapidController {

    @Autowired
    private ItemTyreRapidService itemTyreRapidService;

    @PostMapping
    public ResponseEntity<ItemTyreRapidDTO> saveItemTyreRapid(@RequestBody ItemTyreRapidDTO itemTyreRapidDTO) {
        return ResponseEntity.ok(itemTyreRapidService.saveItemTyreRapid(itemTyreRapidDTO));
    }

    @GetMapping
    public List<ItemTyreRapidDTO> ItemTyreRapid() {
        return itemTyreRapidService.getAllItems();
    }

    @PutMapping
    public ResponseEntity<ItemTyreRapidDTO> UpdateItemTyreRapid(@RequestBody ItemTyreRapidDTO itemTyreRapidDTO) {
        return ResponseEntity.ok(itemTyreRapidService.updateItemTyreRapid(itemTyreRapidDTO));
    }

    @DeleteMapping
    public boolean deleteUser(@RequestBody ItemTyreRapidDTO itemTyreRapidDTO) {
        return itemTyreRapidService.deleteItemTyreRapid(itemTyreRapidDTO);
    }
    
}
