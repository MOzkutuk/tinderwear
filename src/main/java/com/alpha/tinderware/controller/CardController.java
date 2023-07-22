package com.alpha.tinderware.controller;

import com.alpha.tinderware.dao.CardDAO;
import com.alpha.tinderware.dao.ProductDAO;
import com.alpha.tinderware.dao.UserDAO;
import com.alpha.tinderware.entity.Card;
import com.alpha.tinderware.entity.Product;
import com.alpha.tinderware.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    private UserDAO userRepository;

    @Autowired
    private ProductDAO productRepository;

    @Autowired
    private CardDAO cardRepository;

    @PostMapping("/addProductToCard")
    public ResponseEntity<String> addProductToCard(@RequestParam int userId, @RequestParam int productId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        // Check if the product is already in the user's card
        Optional<Card> existingEntry = cardRepository.findByUserAndProduct(optionalUser.get(), optionalProduct.get());

        if (existingEntry.isPresent()) {
            return ResponseEntity.badRequest().body("Product already in card");
        }

        Card card = new Card();
        card.setUser(optionalUser.get());
        card.setProduct(optionalProduct.get());

        cardRepository.save(card);
        return ResponseEntity.ok("Product added to card successfully");
    }

}
