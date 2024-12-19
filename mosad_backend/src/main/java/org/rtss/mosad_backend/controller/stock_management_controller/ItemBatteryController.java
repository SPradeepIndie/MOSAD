package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.stock_management_dto.ItemBatteryDTO;
import org.rtss.mosad_backend.service.stock_management_service.ItemBatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock/battery")
@CrossOrigin
//api/v1/item/battery/saveUser
public class ItemBatteryController {

    @Autowired
    private ItemBatteryService itemBatteryService;

    @PostMapping
    public ResponseEntity<ItemBatteryDTO>  saveItemBattery(@RequestBody ItemBatteryDTO itemBatteryDTO) {
        return ResponseEntity.ok(itemBatteryService.saveItem(itemBatteryDTO));
    }

    @GetMapping
    public List<ItemBatteryDTO> ItemBattery() {
        return itemBatteryService.getAllItems();
    }

    @PutMapping
    public ResponseEntity<ItemBatteryDTO> UpdateItemBattery(@RequestBody ItemBatteryDTO itemBatteryDTO) {
        return ResponseEntity.ok(itemBatteryService.saveItem(itemBatteryDTO));
    }

    @DeleteMapping
    public boolean deleteUser(@RequestBody ItemBatteryDTO itemBatteryDTO) {
        return itemBatteryService.deleteUser(itemBatteryDTO);
    }

}
