package com.alpha.tinderware.controller;

import com.alpha.tinderware.dao.ProductDAO;
import com.alpha.tinderware.dao.TrendingStylesDAO;
import com.alpha.tinderware.entity.Product;
import com.alpha.tinderware.entity.TrendingStyles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TrendingStylesController {

    @Autowired
    private TrendingStylesDAO trendingStylesRepository;

    @Autowired
    private ProductDAO productRepository;

    @GetMapping("/getAllTrendingStyles")
    public ResponseEntity<List<Product>> getAllTrendingStyles() {

        // Fetch all trending styles
        List<TrendingStyles> trendingStylesList = trendingStylesRepository.findAll();

        // Extract products from trending styles
        List<Product> products = trendingStylesList.stream()
                .map(TrendingStyles::getProduct)
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

    @PostMapping("/addTrendingStyle")
    public ResponseEntity<String> addProductToTrendingStyles(@RequestParam int id_product) {

        Optional<Product> optionalProduct = productRepository.findById(id_product);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        // Check if the product is already in Trending Styles
        if (trendingStylesRepository.existsByProduct(optionalProduct.get())) {
            return ResponseEntity.badRequest().body("Product already in Trending Styles");
        }

        TrendingStyles trendingStyle = new TrendingStyles();
        trendingStyle.setProduct(optionalProduct.get());

        trendingStylesRepository.save(trendingStyle);

        return ResponseEntity.ok("Successfully added product to Trending Styles");
    }
}