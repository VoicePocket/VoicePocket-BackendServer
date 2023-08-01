package com.vp.voicepocket.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Configuration
public class FirebaseConfiguration {
    private FirebaseApp firebaseApp;

    @PostConstruct
    void init() throws IOException{
        InputStream refreshToken = new ClassPathResource("firebase/voicepocket_firebase_service_key.json").getInputStream();
        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();
        if (firebaseAppList != null && !firebaseAppList.isEmpty()){
            for(FirebaseApp app:firebaseAppList){
                if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) firebaseApp = app;
            }
        } else{
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }
    }
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException{
        return FirebaseMessaging.getInstance(Objects.requireNonNull(firebaseApp));
    }

    @Bean
    Firestore firestore(){
        return FirestoreClient.getFirestore(firebaseApp);
    }
}
