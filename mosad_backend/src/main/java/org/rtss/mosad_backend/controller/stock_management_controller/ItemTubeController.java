package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.stock_management_dto.ItemTubeDTO;
import org.rtss.mosad_backend.service.stock_management_service.ItemTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/stock/tube")
@CrossOrigin
//api/v1/item/Tube/saveUser
public class ItemTubeController {

    @Autowired
    private ItemTubeService itemTubeService;

    @PostMapping
    public ResponseEntity<ItemTubeDTO> saveItemTube(@RequestBody ItemTubeDTO itemTubeDTO) {
        return ResponseEntity.ok(itemTubeService.saveItem(itemTubeDTO));
    }

    @GetMapping
    public List<ItemTubeDTO> ItemTube() {
        return itemTubeService.getAllItems();
    }

    @PutMapping
    public ResponseEntity<ItemTubeDTO> UpdateItemTube(@RequestBody ItemTubeDTO itemTubeDTO) {
        return ResponseEntity.ok(itemTubeService.saveItem(itemTubeDTO));
    }

    @DeleteMapping
    public boolean deleteUser(@RequestBody ItemTubeDTO itemTubeDTO) {
        return itemTubeService.deleteUser(itemTubeDTO);
    }

}
