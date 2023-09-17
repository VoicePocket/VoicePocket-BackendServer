package com.vp.voicepocket.domain.firebase.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirestoreService {
    private final Firestore firestore;

    public void addWavUrl(String userEmail, String modelEmail, String wavUrl, String uuid){
        String GCP_OPEN_URL = "https://storage.googleapis.com/voice_pocket_egg/";
        DocumentReference docRef = firestore
                .collection("users")
                .document(userEmail)
                .collection(userEmail.equals(modelEmail)?"message":modelEmail)
                .document(uuid);
        Map<String, Object> data = new HashMap<>();
        data.put("message", GCP_OPEN_URL +wavUrl);
        data.put("sender", "SERVER");
        data.put("time", System.currentTimeMillis());
        ApiFuture<WriteResult> result = docRef.set(data);
    }
}