package com.taidt9.inventoryservice.controller;

import com.taidt9.inventoryservice.entity.Inventory;
import com.taidt9.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/stock")
    public Inventory addStock(@RequestParam Long productId,
                              @RequestParam Integer quantity) {
        return inventoryService.addStock(productId, quantity);
    }

    @GetMapping("/{productId}")
    public Integer getStock(@PathVariable Long productId) {
        return inventoryService.getStock(productId);
    }
}