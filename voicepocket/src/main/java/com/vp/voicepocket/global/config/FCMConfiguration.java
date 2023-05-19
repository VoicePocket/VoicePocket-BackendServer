package com.vp.voicepocket.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Configuration
public class FCMConfiguration {
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException{
        //TODO: Add Firebase Private admin Key Path on FIREBASE_PATH!
        ClassPathResource resource = new ClassPathResource("FIREBASE_PATH");
        InputStream refreshToken = resource.getInputStream();

        FirebaseApp firebaseApp=null;
        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();

        if (firebaseAppList != null && !firebaseAppList.isEmpty()){
            for(FirebaseApp app:firebaseAppList){
                if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) firebaseApp = app;
            }
        } else{
            FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(refreshToken)).build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }

        return FirebaseMessaging.getInstance(Objects.requireNonNull(firebaseApp));
    }
}
