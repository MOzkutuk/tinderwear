package com.alpha.tinderware.service;

import com.alpha.tinderware.dao.UserDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;


}
