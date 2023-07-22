package com.alpha.tinderware.controller;

import com.alpha.tinderware.dao.OutfitDAO;
import com.alpha.tinderware.dao.ProductDAO;
import com.alpha.tinderware.entity.Outfit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class OutfitController {

    @Autowired
    private ProductDAO productRepository;

    @Autowired
    private OutfitDAO outfitRepository;

    @PostMapping("/addOutfits")
    public ResponseEntity<String> addProductsToOutfit(@RequestParam int id_product_1,
                                                      @RequestParam int id_product_2,
                                                      @RequestParam int id_product_3) {
        // Ensure the products exist
        if (!productRepository.existsById(id_product_1) ||
                !productRepository.existsById(id_product_2) ||
                !productRepository.existsById(id_product_3)) {
            return ResponseEntity.badRequest().body("One or more products not found");
        }

        // Create a new outfit with the product IDs
        Outfit outfit = new Outfit();
        outfit.setProductIds(id_product_1 + "," + id_product_2 + "," + id_product_3);

        outfitRepository.save(outfit);

        return ResponseEntity.ok("Successfully added products to outfit");
    }

    @GetMapping("/getAllOutfits")
    public ResponseEntity<List<List<Integer>>> getAllOutfits() {

        List<Outfit> outfits = outfitRepository.findAll();

        // Convert outfits into a list of product lists
        List<List<Integer>> result = outfits.stream()
                .map(Outfit::getProductList)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
