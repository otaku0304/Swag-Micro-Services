package swagger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import swagger.converter.SwaggerConverter;
import swagger.dto.HttpResponseDTO;
import swagger.dto.SwaggerDTO;
import swagger.entity.Swagger;
import swagger.repository.SwaggerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SwaggerService {
    @Value("${swag.service.url}")
    private String swagServiceUrl;
    @Value("${swaggest.service.url}")
    private String swaggestServiceUrl;
    private final RestTemplate restTemplate;
    private final SwaggerRepository swaggerRepository;

    @Autowired
    public SwaggerService(SwaggerRepository swaggerRepository, RestTemplateBuilder restTemplateBuilder) {
        this.swaggerRepository = swaggerRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    public HttpResponseDTO save(final SwaggerDTO swaggerDTO) {
        HttpResponseDTO httpResponseDTO = new HttpResponseDTO();
        Swagger swag = swaggerRepository.save(SwaggerConverter.convertSwaggerDTOtoEntity(swaggerDTO));
        if (!ObjectUtils.isEmpty(swag)) {
            httpResponseDTO.setResponseCode(201);
            httpResponseDTO.setResponseMessage("Swagger Saved successfully");
        } else {
            httpResponseDTO.setResponseCode(400);
            httpResponseDTO.setResponseMessage("BAD Request");
            return httpResponseDTO;
        }
        return httpResponseDTO;
    }

    public HttpResponseDTO fetch(String user) {
        HttpResponseDTO httpResponseDTO = new HttpResponseDTO();
        List<Swagger> swaggerList = swaggerRepository.findByUser(user);
        if (!swaggerList.isEmpty()) {
            List<String> swaggerContents = new ArrayList<>();
            for (Swagger swagger : swaggerList) {
                swaggerContents.add(swagger.getSwaggerContent());
            }
            httpResponseDTO.setResponseCode(200);
            httpResponseDTO.setResponseMessage("STATUS_200");
            httpResponseDTO.setResponseBody(swaggerContents);
        } else {
            httpResponseDTO.setResponseCode(404);
            httpResponseDTO.setResponseMessage("STATUS_404: User not found");
        }
        return httpResponseDTO;
    }

    public HttpResponseDTO fetchSwag(final String user) {
        String url = swagServiceUrl + "/swag/fetch?user=" + user;
        ResponseEntity<HttpResponseDTO> responseEntity = restTemplate.getForEntity(url, HttpResponseDTO.class);
        return responseEntity.getBody();
    }


    public HttpResponseDTO fetchSwaggest(final String user) {
        String url = swaggestServiceUrl + "/swaggest/fetch?user=" + user;
        ResponseEntity<HttpResponseDTO> responseEntity = restTemplate.getForEntity(url, HttpResponseDTO.class);
        return responseEntity.getBody();
    }

}
