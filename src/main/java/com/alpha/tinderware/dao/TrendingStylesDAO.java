package com.alpha.tinderware.dao;

import com.alpha.tinderware.entity.Product;
import com.alpha.tinderware.entity.TrendingStyles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrendingStylesDAO extends JpaRepository<TrendingStyles, Integer> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM TrendingStyles t WHERE t.product = :product")
    boolean existsByProduct(Product product);

}
