package com.alpha.tinderware.dao;

import com.alpha.tinderware.entity.BottomWear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BottomWearRepository extends JpaRepository<BottomWear, Integer> {

    Optional<BottomWear> findByIdp(int idp);
}
