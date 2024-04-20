package com.swagger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.swagger.dto.HttpResponseDTO;
import com.swagger.dto.SwaggerDTO;
import com.swagger.service.SwaggerService;

@CrossOrigin
@RestController
@RequestMapping("/swagger")
class SwaggerController {

    private final SwaggerService swaggerService;

    @Autowired
    private SwaggerController(SwaggerService swaggerService) {
        this.swaggerService = swaggerService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<HttpResponseDTO> saveSwagger(@RequestBody final SwaggerDTO swagDTO) {
        HttpResponseDTO httpResponseDTO = swaggerService.save(swagDTO);
        return new ResponseEntity<>(httpResponseDTO, HttpStatus.valueOf(httpResponseDTO.getResponseCode()));
    }

    @GetMapping(value = "/fetch")
    public ResponseEntity<HttpResponseDTO> fetch(@RequestParam final String user) {
        HttpResponseDTO httpResponseDTO = swaggerService.fetch(user);
        return new ResponseEntity<>(httpResponseDTO, HttpStatus.valueOf(httpResponseDTO.getResponseCode()));
    }

    @GetMapping(value = "/fetch-swag")
    public ResponseEntity<HttpResponseDTO> fetchSwag(@RequestParam final String user) {
        HttpResponseDTO httpResponseDTO = swaggerService.fetchSwag(user);
        return new ResponseEntity<>(httpResponseDTO, HttpStatus.valueOf(httpResponseDTO.getResponseCode()));
    }

    @GetMapping(value = "/fetch-swaggest")
    public ResponseEntity<HttpResponseDTO> fetchSwaggest(@RequestParam final String user) {
        HttpResponseDTO httpResponseDTO = swaggerService.fetchSwaggest(user);
        return new ResponseEntity<>(httpResponseDTO, HttpStatus.valueOf(httpResponseDTO.getResponseCode()));
    }
}

