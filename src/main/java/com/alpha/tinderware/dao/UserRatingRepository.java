package com.alpha.tinderware.dao;

import com.alpha.tinderware.entity.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRatingRepository extends JpaRepository<UserRating, Integer> {

    List<UserRating> findById(int userId);

    List<UserRating> findByIdu(int idu);

}
