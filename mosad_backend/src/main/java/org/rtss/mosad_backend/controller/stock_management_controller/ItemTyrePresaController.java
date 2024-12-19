package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyrePresaDTO;
import org.rtss.mosad_backend.service.stock_management_service.ItemTyrePresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/stock/tyre_presa")
@CrossOrigin

public class ItemTyrePresaController {

    @Autowired
    private ItemTyrePresaService itemTyrePresaService;

    @PostMapping
    public ResponseEntity<ItemTyrePresaDTO> saveItemTyrePresa(@RequestBody ItemTyrePresaDTO itemTyrePresaDTO) {
        return ResponseEntity.ok(itemTyrePresaService.saveItemTyrePresa(itemTyrePresaDTO));
    }

    @GetMapping
    public List<ItemTyrePresaDTO> ItemTyrePresa() {
        return itemTyrePresaService.getAllItems();
    }

    @PutMapping
    public ResponseEntity<ItemTyrePresaDTO> UpdateItemTyrePresa(@RequestBody ItemTyrePresaDTO itemTyrePresaDTO) {
        return ResponseEntity.ok(itemTyrePresaService.saveItemTyrePresa(itemTyrePresaDTO));
    }

    @DeleteMapping
    public boolean deleteUser(@RequestBody ItemTyrePresaDTO itemTyrePresaDTO) {
        return itemTyrePresaService.deleteItemTyrePresa(itemTyrePresaDTO);
    }
    
}
