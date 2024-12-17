package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.stock_management_dto.ItemOtherAccessoriesDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemOtherAccessoriesDTO;
import org.rtss.mosad_backend.service.stock_management_service.ItemOtherAccessoriesService;
import org.rtss.mosad_backend.service.stock_management_service.ItemOtherAccessoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/stock/otheraccessories")
@CrossOrigin
public class ItemOtherAccessoriesController {

    @Autowired
    private ItemOtherAccessoriesService itemOtherAccessoriesService;

    @PostMapping
    public ResponseEntity<ItemOtherAccessoriesDTO> saveItemOtherAccessories(@RequestBody ItemOtherAccessoriesDTO itemOtherAccessoriesDTO) {
        return ResponseEntity.ok(itemOtherAccessoriesService.saveItem(itemOtherAccessoriesDTO));
    }

    @GetMapping
    public List<ItemOtherAccessoriesDTO> ItemOtherAccessories() {
        return itemOtherAccessoriesService.getAllItems();
    }

    @PutMapping
    public ResponseEntity<ItemOtherAccessoriesDTO> UpdateItemOtherAccessories(@RequestBody ItemOtherAccessoriesDTO itemOtherAccessoriesDTO) {
        return ResponseEntity.ok(itemOtherAccessoriesService.saveItem(itemOtherAccessoriesDTO));
    }

    @DeleteMapping
    public boolean deleteUser(@RequestBody ItemOtherAccessoriesDTO itemOtherAccessoriesDTO) {
        return itemOtherAccessoriesService.deleteUser(itemOtherAccessoriesDTO);
    }
    
}
