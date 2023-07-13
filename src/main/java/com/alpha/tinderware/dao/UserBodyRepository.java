package com.alpha.tinderware.dao;

import com.alpha.tinderware.entity.UserBody;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBodyRepository extends JpaRepository<UserBody, Integer> {

    Optional<UserBody> findByIdu(int idu);
}
