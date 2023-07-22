package com.alpha.tinderware.dao;

import com.alpha.tinderware.entity.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutfitDAO extends JpaRepository<Outfit, Integer> {
}