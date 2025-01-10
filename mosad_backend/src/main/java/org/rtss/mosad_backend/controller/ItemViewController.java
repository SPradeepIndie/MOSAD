package org.rtss.mosad_backend.controller;

import org.rtss.mosad_backend.service.stock_management_service.ItemViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item-view")
public class ItemViewController{


    @Autowired
    private ItemViewService itemViewService;

    @GetMapping
    public ResponseEntity<?> getItems(
            @RequestParam String category,
            @RequestParam(required = false) String brand) {
        try {
            List<?> items = itemViewService.getItems(category, brand);
            return ResponseEntity.ok(items);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
