package com.alpha.tinderware.dao;

import com.alpha.tinderware.entity.Product;
import com.alpha.tinderware.entity.User;
import com.alpha.tinderware.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishListDAO extends JpaRepository<WishList, Integer> {
    Optional<WishList> findByUserAndProduct(User user, Product product);
    @Query("SELECT w FROM WishList w JOIN FETCH w.product WHERE w.user = :user")
    List<WishList> findByUser(@Param("user") User user);

}