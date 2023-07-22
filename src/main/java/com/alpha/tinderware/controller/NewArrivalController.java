package com.alpha.tinderware.controller;

import com.alpha.tinderware.dao.NewArrivalDAO;
import com.alpha.tinderware.dao.ProductDAO;
import com.alpha.tinderware.entity.NewArrival;
import com.alpha.tinderware.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class NewArrivalController {

    @Autowired
    private ProductDAO productRepository;

    @Autowired
    private NewArrivalDAO newArrivalRepository;

    @PostMapping("/addNewArrivals")
    public ResponseEntity<String> addProductToNewArrivals(@RequestParam int id_product) {

        Optional<Product> optionalProduct = productRepository.findById(id_product);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        // Check if the product is already in the New Arrivals
        if (newArrivalRepository.existsByProduct(optionalProduct.get())) {
            return ResponseEntity.badRequest().body("Product already in New Arrivals");
        }

        NewArrival newArrival = new NewArrival();
        newArrival.setProduct(optionalProduct.get());

        newArrivalRepository.save(newArrival);

        return ResponseEntity.ok("Successfully added product to New Arrivals");
    }

    @GetMapping("/getAllNewArrivals")
    public ResponseEntity<List<Product>> getAllNewArrivals() {

        // Fetch all new arrivals
        List<NewArrival> newArrivals = newArrivalRepository.findAll();

        // Extract products from new arrivals
        List<Product> products = newArrivals.stream()
                .map(NewArrival::getProduct)
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }


}
