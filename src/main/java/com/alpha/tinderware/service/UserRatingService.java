package com.alpha.tinderware.service;

import com.alpha.tinderware.dao.ProjectDBContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserRatingService {

    @Autowired
    private ProjectDBContext projectDBContext;
}
