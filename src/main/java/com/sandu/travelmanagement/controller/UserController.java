package com.sandu.travelmanagement.controller;

import com.sandu.travelmanagement.model.UserEntity;
import com.sandu.travelmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody UserEntity newUser) {
        return service.register(newUser);
    }

    @GetMapping("/login")
    public ResponseEntity<UserEntity> login(@RequestBody UserEntity user) {
        return service.login(user);
    }
    @DeleteMapping("/deleteAcc")
    public ResponseEntity<UserEntity> deleteAcc(@RequestBody UserEntity user) {
        return service.deleteAcc(user);
    }
}
