package com.alpha.tinderware.controller;

import com.alpha.tinderware.dao.UserDAO;
import com.alpha.tinderware.entity.User;
import com.alpha.tinderware.model.LoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController ("/user/controller")
public class UserController {

//    @Autowired
//    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody User user){

        userDAO.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/showUsers")
    public List<User> getAllUsers(Model model){

        return userDAO.findAll();
    }

    @GetMapping("/getUser/{id}")
    public User getUserById(@PathVariable int id){

        return userDAO.findById(id).orElse(null);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id){

        User user = userDAO.findById(id).orElse(null);
        userDAO.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateStudent(@PathVariable int id, @RequestBody User user){

        Optional<User> userbyId = userDAO.findById(id);

        if (userbyId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User newUser = userbyId.get();
        newUser.setFirstName(user.getFirstName());
        newUser.setPassword(user.getPassword());
        newUser.setLastName(user.getLastName());
        newUser.setGender(user.getGender());
        newUser.setEmail(user.getEmail());
        newUser.setAddress(user.getAddress());
        newUser.setCountry(user.getCountry());
        newUser.setIsActive(user.getIsActive());
        newUser.setAge(user.getAge());

        userDAO.save(newUser);

        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginModel loginModel) {
        //Creating user object based on the JPA query result
//        Optional<User> userOptional = userDAO.findByEmail(loginModel.getEmail());
        User byEmail = userDAO.findByEmail(loginModel.getEmail());

        //Comparing credentials between user and loginModel objects
        if (byEmail == null || !byEmail.getPassword().equals(loginModel.getPassword())) {

//            logger.warn("Failed login attempt for user with email {}.", loginModel.getEmail());

            return ResponseEntity.badRequest().body("Invalid email or password");
        }

//        logger.info("User with email {} logged in.", loginModel.getEmail());
        return ResponseEntity.ok("Logged in hurrayyy!");
    }

}
