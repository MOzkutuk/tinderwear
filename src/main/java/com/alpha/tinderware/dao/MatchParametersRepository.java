package com.alpha.tinderware.dao;

import com.alpha.tinderware.entity.MatchParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MatchParametersRepository extends JpaRepository<MatchParameters, Integer> {

    Optional<MatchParameters> findByIdp(int idp);

    @Query("SELECT m FROM MatchParameters m WHERE m.collorPalette = ?1 OR m.bodyShape = ?2 OR m.height = ?3")
    List<MatchParameters> findMatchByParameters(String colorPalette, String bodyShape, String height);



}
