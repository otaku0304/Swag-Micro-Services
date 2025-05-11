package com.swag.controller;

import com.swag.service.FireStoreService;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SwagControllerTest {
    @Mock
    private SwagController swagController;

    @InjectMocks
    private FireStoreService firebaseService;
}
