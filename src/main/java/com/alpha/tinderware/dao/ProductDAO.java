package com.alpha.tinderware.dao;

import com.alpha.tinderware.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product,Integer> {

    List<Product> findAllByIdInAndGender(List<Integer> ids, String gender);

}
