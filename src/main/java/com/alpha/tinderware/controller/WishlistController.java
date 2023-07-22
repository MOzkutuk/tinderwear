package com.alpha.tinderware.controller;

import com.alpha.tinderware.dao.ProductDAO;
import com.alpha.tinderware.dao.UserDAO;
import com.alpha.tinderware.dao.WishListDAO;
import com.alpha.tinderware.entity.Product;
import com.alpha.tinderware.entity.User;
import com.alpha.tinderware.entity.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class WishlistController {

    @Autowired
    private UserDAO userRepository;

    @Autowired
    private ProductDAO productRepository;

    @Autowired
    private WishListDAO wishListRepository;

    @PostMapping("/addProductToWishlist")
    public ResponseEntity<String> addProductToWishlist(@RequestParam int userId, @RequestParam int productId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        // Check if the product is already in the user's wishlist
        Optional<WishList> existingEntry = wishListRepository.findByUserAndProduct(optionalUser.get(), optionalProduct.get());

        if (existingEntry.isPresent()) {
            return ResponseEntity.badRequest().body("Product already in wishlist");
        }

        WishList wishList = new WishList();
        wishList.setUser(optionalUser.get());
        wishList.setProduct(optionalProduct.get());

        wishListRepository.save(wishList);
        return ResponseEntity.ok("Product added to wishlist successfully");
    }

    @GetMapping("/getProductsFromUserWishlist")
    public ResponseEntity<List<Product>> getProductsInWishlist(@RequestParam int userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        User user = optionalUser.get();
        List<WishList> wishlistEntries = wishListRepository.findByUser(user);

        // Extract products from wishlist entries
        List<Product> products = wishlistEntries.stream()
                .map(WishList::getProduct)
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/deleteProductFromWishlist")
    public ResponseEntity<String> removeProductFromWishlist(@RequestParam int userId, @RequestParam int productId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        // Check if the product is actually in the user's wishlist
        Optional<WishList> existingEntry = wishListRepository.findByUserAndProduct(optionalUser.get(), optionalProduct.get());

        if (existingEntry.isEmpty()) {
            return ResponseEntity.badRequest().body("Product not in wishlist");
        }

        wishListRepository.delete(existingEntry.get());
        return ResponseEntity.ok("Product removed from wishlist successfully");
    }
}
