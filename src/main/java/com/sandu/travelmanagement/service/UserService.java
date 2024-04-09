package com.sandu.travelmanagement.service;

import com.sandu.travelmanagement.model.UserEntity;
import com.sandu.travelmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public ResponseEntity<UserEntity> register(UserEntity newUser) {
        if(repo.findByUserName(newUser.getUserName()) != null && repo.findByEmail(newUser.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.SEE_OTHER);
        }
        try {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
            if(newUser.getEmail().matches(emailRegex) && newUser.getPassword().matches(passwordRegex) && newUser.getUserName().length() < 3) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            String hashedPassword  = passwordEncoder.encode(newUser.getPassword());
            newUser.setPassword(hashedPassword);
            repo.save(newUser);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(newUser ,HttpStatus.CREATED);
    }

    public ResponseEntity<UserEntity> login(UserEntity user) {
        UserEntity exist = repo.findByEmail(user.getEmail());
        if (exist == null) {
            return new ResponseEntity<>(exist, HttpStatus.NOT_FOUND);
        }
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (passwordEncoder.matches(user.getPassword(), exist.getPassword())) {
            return new ResponseEntity<>(exist, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<UserEntity> deleteAcc(UserEntity user) {
        UserEntity exist = repo.findByUserName(user.getUserName());
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (passwordEncoder.matches(user.getPassword(), exist.getPassword())) {
            repo.deleteById(exist.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
