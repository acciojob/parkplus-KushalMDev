package com.driver.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.driver.model.User;
import com.driver.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {
    
    UserServiceImpl userService=new UserServiceImpl();
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestParam String name, @RequestParam String phoneNumber, @RequestParam String password){
        userService.register(name, phoneNumber, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updatePassword(@RequestParam Integer userId, @RequestParam String password){
        User updatedUser=userService.updatePassword(userId, password);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam Integer userId){
        userService.deleteUser(userId);
    }
}
