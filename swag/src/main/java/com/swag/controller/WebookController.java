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
    public ResponseEntity<String> test(@RequestBody String payload){
        System.out.println("Hello"+ payload);
        System.out.println("Bye");
        System.out.println("GoodBye");
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }
}
