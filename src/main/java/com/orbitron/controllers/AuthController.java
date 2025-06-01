package com.orbitron.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.orbitron.dto.LoginAuthDTO;
import com.orbitron.entities.entities.Role;
import com.orbitron.entities.entities.Users;
import com.orbitron.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private UserRepository userRepository;


    @PostMapping("/logIn")
    public ResponseEntity<?> verifyToken(HttpServletRequest request, HttpSession session ) {
        String authToken = request.getHeader("Authorization");

        FirebaseToken decodedToken = null;
        String uid = null;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(authToken);
            uid = decodedToken.getUid();
        } catch (FirebaseAuthException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Firebase token verification failed: " + e.getMessage());
        }

        String email = decodedToken.getEmail();


        String appSessionToken = session.getId();

        Users user = userRepository.findByUsername(email);
        Set<Role> roles = user.getRoles(); 

        LoginAuthDTO loginResponseObject = new LoginAuthDTO(appSessionToken, roles);
        
        return ResponseEntity.ok(loginResponseObject);
    }
}
