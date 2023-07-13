package com.alpha.tinderware.dao;

import com.alpha.tinderware.entity.TopWear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopWearRepository extends JpaRepository<TopWear, Integer> {

    Optional<TopWear> findByIdp(int idp);

    List<TopWear> findAllById(int id);

}
