package com.swagger.service;

import com.swagger.globalexceptionshandler.SwaggerServiceException;
import com.swagger.converter.SwaggerConverter;
import com.swagger.dto.HttpResponseDTO;
import com.swagger.dto.SwaggerDTO;
import com.swagger.entity.Swagger;
import com.swagger.repository.SwaggerRepository;
import com.swagger.utils.Utility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SwaggerService {
    @Value("${swag.url}")
    private String swagServiceUrl;
    @Value("${swaggest.appName}")
    private String swaggestServiceUrl;
    private final RestTemplate restTemplate;
    private final SwaggerRepository swaggerRepository;

    public SwaggerService(SwaggerRepository swaggerRepository, RestTemplateBuilder restTemplateBuilder) {
        this.swaggerRepository = swaggerRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    public HttpResponseDTO save(final SwaggerDTO swaggerDTO) {
        HttpResponseDTO httpResponseDTO = new HttpResponseDTO();
        if(ObjectUtils.isEmpty(swaggerDTO.getSwaggerContent()) || ObjectUtils.isEmpty(swaggerDTO.getUser()) ){
            return Utility.setResponseCodeAndMessage(httpResponseDTO, 400, "Swagger data is empty.");
        }
        Swagger swag = swaggerRepository.save(SwaggerConverter.convertSwaggerDTOtoEntity(swaggerDTO));
        if (!ObjectUtils.isEmpty(swag)) {
            return Utility.setResponseCodeAndMessage(httpResponseDTO, 201, "Swagger saved successfully ");
        }
        return httpResponseDTO;
    }

    public HttpResponseDTO fetch(final String user) {
        HttpResponseDTO httpResponseDTO = new HttpResponseDTO();
        if(ObjectUtils.isEmpty(user)){
            return Utility.setResponseCodeAndMessage(httpResponseDTO, 400, "User is empty.");
        }
        List<Swagger> swaggerList = swaggerRepository.findByUser(user);
        if (swaggerList.isEmpty()) {
            return Utility.setResponseCodeAndMessage(httpResponseDTO, 404, "STATUS_404: User not found");
        }
        List<String> swaggerContents = new ArrayList<>();
        for (Swagger swagger : swaggerList) {
            swaggerContents.add(swagger.getSwaggerContent());
        }
        return Utility.setResponseCodeAndMessageWithBody(httpResponseDTO, 200, "STATUS_200",swaggerContents);
    }

    public HttpResponseDTO fetchSwag(final String user) {
        String url = swagServiceUrl + "/swag/fetch?user=" + user;
        try {
            ResponseEntity<HttpResponseDTO> responseEntity = restTemplate.getForEntity(url, HttpResponseDTO.class);
            return responseEntity.getBody();
        } catch (RestClientException ex) {
            throw new SwaggerServiceException(ex.getMessage(), ex);
        }
    }

    public HttpResponseDTO fetchSwaggest(final String user) {
        String url = swaggestServiceUrl + "/swaggest/fetch?user=" + user;
        try {
            ResponseEntity<HttpResponseDTO> responseEntity = restTemplate.getForEntity(url, HttpResponseDTO.class);
            return responseEntity.getBody();
        } catch (RestClientException ex) {
            throw new SwaggerServiceException(ex.getMessage(), ex);
        }
    }
}
