package com.orbitron.config;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initFirebase() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
