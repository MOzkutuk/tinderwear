package com.alpha.tinderware.dao;

import com.alpha.tinderware.entity.Card;
import com.alpha.tinderware.entity.Product;
import com.alpha.tinderware.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardDAO extends JpaRepository<Card, Integer> {
    Optional<Card> findByUserAndProduct(User user, Product product);
}