package com.jwt.jwtcapstone.service;

import com.jwt.jwtcapstone.model.Users;
import com.jwt.jwtcapstone.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepo repo;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {

        if (user.getId() != 0 && repo.existsById(user.getId())) {
            throw new RuntimeException("A user with this ID already exists.");
        }

        if (repo.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("A user with this username already exists.");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return user;
    }

    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }

    public Users getuserdetails(String username) {
        return repo.findByUsername(username);
    }
}
