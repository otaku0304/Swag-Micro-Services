package com.swag.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebookController {

    @PostMapping
    public ResponseEntity<String> test(@RequestBody String requestBody){
        System.out.println("Hello"+ requestBody);
        return new ResponseEntity<>(requestBody, HttpStatus.OK);
    }
}
