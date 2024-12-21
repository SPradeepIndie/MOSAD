package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyreAtlanderDTO;
import org.rtss.mosad_backend.service.stock_management_service.ItemTyreAtlanderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/stock/tyre_atlander")
@CrossOrigin
//api/v1/item/TyreAtlander/saveUser
public class ItemTyreAtlanderConroller{

    @Autowired
    private ItemTyreAtlanderService itemTyreAtlanderService;

    @PostMapping
    public ResponseEntity<ItemTyreAtlanderDTO> saveItemTyreAtlander(@RequestBody ItemTyreAtlanderDTO itemTyreAtlanderDTO) {
        return ResponseEntity.ok(itemTyreAtlanderService.saveItemTyreAtlander(itemTyreAtlanderDTO));
    }

    @GetMapping
    public List<ItemTyreAtlanderDTO> ItemTyreAtlander() {
        return itemTyreAtlanderService.getAllItems();
    }

    @PutMapping
    public ResponseEntity<ItemTyreAtlanderDTO> UpdateItemTyreAtlander(@RequestBody ItemTyreAtlanderDTO itemTyreAtlanderDTO) {
        return ResponseEntity.ok(itemTyreAtlanderService.updateItemTyreAtlander(itemTyreAtlanderDTO));
    }

    @DeleteMapping
    public boolean deleteUser(@RequestBody ItemTyreAtlanderDTO itemTyreAtlanderDTO) {
        return itemTyreAtlanderService.deleteItemTyreAtlander(itemTyreAtlanderDTO);
    }

}