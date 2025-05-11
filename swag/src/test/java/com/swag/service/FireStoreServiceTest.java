package com.swag.service;

import com.google.cloud.firestore.*;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FireStoreServiceTest {
    @Mock
    private Firestore firestore;
    @InjectMocks
    private FireStoreService firebaseService;

}
