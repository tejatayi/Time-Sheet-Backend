package com.timesheet.TSbackend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void initFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream("/Users/saisivatejatayi/Documents/TSbackend/src/main/resources/firebase/kfc-timesheet-firebase-adminsdk-fbsvc-490bc914dd.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            System.out.println("✅ Firebase has been initialized");
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to initialize Firebase", e);
        }
    }
}
