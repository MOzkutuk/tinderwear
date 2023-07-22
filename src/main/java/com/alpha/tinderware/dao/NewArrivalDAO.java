package com.alpha.tinderware.dao;

import com.alpha.tinderware.entity.NewArrival;
import com.alpha.tinderware.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewArrivalDAO extends JpaRepository<NewArrival, Integer> {

    boolean existsByProduct(Product product);
}
