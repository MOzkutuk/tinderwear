package com.alpha.tinderware.dao;

import com.alpha.tinderware.entity.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDBContext extends JpaRepository<UserRating,Integer> {
}
