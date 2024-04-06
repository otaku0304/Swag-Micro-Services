package swagger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swagger.dto.HttpResponseDTO;
import swagger.dto.SwaggerDTO;
import swagger.service.SwaggerService;

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
        if (httpResponseDTO.getResponseCode() == 201) {
            return new ResponseEntity<>(httpResponseDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(httpResponseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/fetch")
    public ResponseEntity<HttpResponseDTO> fetch(@RequestParam final String user) {
        HttpResponseDTO httpResponseDTO = swaggerService.fetch(user);
        if (httpResponseDTO.getResponseCode() == 201) {
            return new ResponseEntity<>(httpResponseDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(httpResponseDTO, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/fetch-swag")
    public ResponseEntity<HttpResponseDTO> fetchSwag(@RequestParam final String user) {
        HttpResponseDTO httpResponseDTO = swaggerService.fetchSwag(user);
        if (httpResponseDTO.getResponseCode() == HttpStatus.CREATED.value()) {
            return new ResponseEntity<>(httpResponseDTO, HttpStatus.CREATED);
        } else if (httpResponseDTO.getResponseCode() == HttpStatus.NOT_FOUND.value()) {
            return new ResponseEntity<>(httpResponseDTO, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(httpResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/fetch-swaggest")
    public ResponseEntity<HttpResponseDTO> fetchSwaggest(@RequestParam final String user) {
        HttpResponseDTO httpResponseDTO = swaggerService.fetchSwaggest(user);
        if (httpResponseDTO.getResponseCode() == 201) {
            return new ResponseEntity<>(httpResponseDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(httpResponseDTO, HttpStatus.NOT_FOUND);
        }
    }
}

