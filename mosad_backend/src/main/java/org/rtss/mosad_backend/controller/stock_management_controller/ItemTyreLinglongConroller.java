package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyreLinglongDTO;
import org.rtss.mosad_backend.service.stock_management_service.ItemTyreLinglongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/stock/tyre_linglong")
@CrossOrigin

public class ItemTyreLinglongConroller {
    @Autowired
    private ItemTyreLinglongService itemTyreLinglongService;

    @PostMapping
    public ResponseEntity<ItemTyreLinglongDTO> saveItemTyreLinglong(@RequestBody ItemTyreLinglongDTO itemTyreLinglongDTO) {
        return ResponseEntity.ok(itemTyreLinglongService.saveItemTyreLinglong(itemTyreLinglongDTO));
    }

    @GetMapping
    public List<ItemTyreLinglongDTO> ItemTyreLinglong() {
        return itemTyreLinglongService.getAllItems();
    }

    @PutMapping
    public ResponseEntity<ItemTyreLinglongDTO> UpdateItemTyreLinglong(@RequestBody ItemTyreLinglongDTO itemTyreLinglongDTO) {
        return ResponseEntity.ok(itemTyreLinglongService.updateItemTyreLinglong(itemTyreLinglongDTO));
    }

    @DeleteMapping
    public boolean deleteUser(@RequestBody ItemTyreLinglongDTO itemTyreLinglongDTO) {
        return itemTyreLinglongService.deleteItemTyreLinglong(itemTyreLinglongDTO);
    }
}
