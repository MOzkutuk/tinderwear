package com.alpha.tinderware.dao;

import com.alpha.tinderware.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserDAO extends JpaRepository<User,Integer> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);



}
