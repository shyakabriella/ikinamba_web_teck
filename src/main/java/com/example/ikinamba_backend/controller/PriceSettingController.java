package com.example.ikinamba_backend.controller;

import com.example.ikinamba_backend.model.PriceSetting;
import com.example.ikinamba_backend.repository.PriceSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price_settings")
public class PriceSettingController {

    @Autowired
    private PriceSettingRepository priceSettingRepository;

    @GetMapping
    public List<PriceSetting> getAllPriceSettings() {
        return priceSettingRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<PriceSetting> createPriceSetting(@RequestBody PriceSetting priceSetting) {
        PriceSetting savedPriceSetting = priceSettingRepository.save(priceSetting);
        return ResponseEntity.ok(savedPriceSetting);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceSetting> updatePriceSetting(@PathVariable Long id, @RequestBody PriceSetting priceSetting) {
        return priceSettingRepository.findById(id).map(existingPriceSetting -> {
            existingPriceSetting.setCarSize(priceSetting.getCarSize());
            existingPriceSetting.setCarModel(priceSetting.getCarModel());
            existingPriceSetting.setPrice(priceSetting.getPrice());
            priceSettingRepository.save(existingPriceSetting);
            return ResponseEntity.ok(existingPriceSetting);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePriceSetting(@PathVariable Long id) {
        return priceSettingRepository.findById(id).map(priceSetting -> {
            priceSettingRepository.delete(priceSetting);
            return ResponseEntity.ok().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
