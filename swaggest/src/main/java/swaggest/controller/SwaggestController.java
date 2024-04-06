package swaggest.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import swaggest.dto.HttpResponseDTO;
import swaggest.dto.SwaggestDTO;
import swaggest.service.SwaggestService;

@CrossOrigin
@RestController
@RequestMapping("/swaggest")
@Slf4j
public class SwaggestController {
    private final SwaggestService swaggestService;

    @Autowired
    private SwaggestController(SwaggestService swaggestService) {
        this.swaggestService = swaggestService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<HttpResponseDTO> save( @RequestBody final SwaggestDTO swagDTO)  {
        log.info("swaggest-controller: {}", swagDTO);
        HttpResponseDTO  httpResponseDTO = swaggestService.save(swagDTO);
        return new ResponseEntity<>(httpResponseDTO, HttpStatus.valueOf(httpResponseDTO.getResponseCode()));
    }

    @GetMapping(value = "/fetch")
    public ResponseEntity<HttpResponseDTO> fetch(@RequestParam final String user) {
        HttpResponseDTO httpResponseDTO = swaggestService.fetch(user);
        return new ResponseEntity<>(httpResponseDTO, HttpStatus.valueOf(httpResponseDTO.getResponseCode()));
    }

    @GetMapping(value = "/call-fetch-swag")
    public ResponseEntity<HttpResponseDTO> callFetchSwag(@RequestParam final String user) {
        HttpResponseDTO httpResponseDTO = swaggestService.callFetchSwag(user);
        return new ResponseEntity<>(httpResponseDTO, HttpStatus.valueOf(httpResponseDTO.getResponseCode()));
    }

    @GetMapping(value = "/call-fetch-swagger")
    public ResponseEntity<HttpResponseDTO> callFetchSwagger(@RequestParam final String user) {
        HttpResponseDTO httpResponseDTO = swaggestService.callFetchSwagger(user);
        return new ResponseEntity<>(httpResponseDTO, HttpStatus.valueOf(httpResponseDTO.getResponseCode()));
    }
}
