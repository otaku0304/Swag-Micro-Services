package com.swag.controller;

import com.swag.service.SwagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.swag.dto.HttpResponseDTO;
import com.swag.dto.SwagDTO;
import com.swag.dto.SwaggestDTO;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


@RestController
@CrossOrigin
@RequestMapping("/com/swag")
public class SwagController {

    private final SwagService swagService;

    @Autowired
    private SwagController(SwagService swagService) {
        this.swagService = swagService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<HttpResponseDTO> save(@RequestBody final SwagDTO swagDTO) throws InterruptedException, ExecutionException {
        HttpResponseDTO httpResponseDTO = swagService.save(swagDTO);
        return new ResponseEntity<>(httpResponseDTO, HttpStatus.valueOf(httpResponseDTO.getResponseCode()));
    }

    @GetMapping(value = "/fetch")
    public ResponseEntity<HttpResponseDTO> fetch(@RequestParam final String user) throws InterruptedException, ExecutionException {
        HttpResponseDTO httpResponseDTO = swagService.fetch(user);
       return new ResponseEntity<>(httpResponseDTO, HttpStatus.valueOf(httpResponseDTO.getResponseCode()));
    }

    @PostMapping(value = "/save-swagger")
    public ResponseEntity<HttpResponseDTO> saveSwagger(@RequestBody final SwagDTO swagDTO) {
        HttpResponseDTO httpResponseDTO = swagService.saveSwagger(swagDTO);
       return new ResponseEntity<>(httpResponseDTO, HttpStatus.valueOf(httpResponseDTO.getResponseCode()));
    }

    @PostMapping(value = "/save-swaggest")
    public ResponseEntity<HttpResponseDTO> saveSwaggest(@RequestBody final SwaggestDTO swaggestDTO) {
        HttpResponseDTO httpResponseDTO = swagService.saveSwaggest(swaggestDTO);
       return new ResponseEntity<>(httpResponseDTO, HttpStatus.valueOf(httpResponseDTO.getResponseCode()));
    }

    @DeleteMapping("/user-delete-swag-content")
    public ResponseEntity<HttpResponseDTO> deleteSwagForUser(@RequestBody final SwagDTO swagDTO) throws InterruptedException, ExecutionException {
        HttpResponseDTO httpResponseDTO = swagService.deleteSwagForUser(swagDTO);
        return new ResponseEntity<>(httpResponseDTO, HttpStatus.valueOf(httpResponseDTO.getResponseCode()));
    }
}
