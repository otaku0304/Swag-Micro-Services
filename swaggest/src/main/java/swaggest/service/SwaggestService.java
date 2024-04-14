package swaggest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import swaggest.globalexceptionshandler.SwaggestServiceException;
import swaggest.converter.SwaggestConverter;
import swaggest.dto.HttpResponseDTO;
import swaggest.dto.SwaggestDTO;
import swaggest.entity.Swaggest;
import swaggest.repository.SwaggestRepository;
import swaggest.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Slf4j
public class SwaggestService {
    @Value("${swag.service.url}")
    private String swagServiceUrl;
    @Value("${swagger.service.url}")
    private String swaggerServiceUrl;
    private final RestTemplate restTemplate;
    private final SwaggestRepository swaggestRepository;

    public SwaggestService(SwaggestRepository swaggestRepository, RestTemplateBuilder restTemplateBuilder) {
        this.swaggestRepository = swaggestRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    public HttpResponseDTO save(final SwaggestDTO swaggestDTO) {
        HttpResponseDTO httpResponseDTO = new HttpResponseDTO();
        if (swaggestDTO.isEmpty()) {
            return Utility.setResponseCodeAndMessage(httpResponseDTO, 400, "Swaggest data is empty.");
        }
        Swaggest swag = swaggestRepository.save(SwaggestConverter.convertSwagDTOtoEntity(swaggestDTO));
        if (!ObjectUtils.isEmpty(swag)) {
            return Utility.setResponseCodeAndMessage(httpResponseDTO, 201, "Swaggest created successfully.");
        }
        return httpResponseDTO;
    }


    public HttpResponseDTO fetch(final String user) {
        HttpResponseDTO httpResponseDTO = new HttpResponseDTO();
        List<Swaggest> swaggestList = swaggestRepository.findByUser(user);
        if (!swaggestList.isEmpty()) {
            List<String> swaggestContents = new ArrayList<>();
            for (Swaggest swaggest : swaggestList) {
                swaggestContents.add(swaggest.getSwaggestContent());
            }
            httpResponseDTO.setResponseCode(201);
            httpResponseDTO.setResponseMessage("STATUS_201");
            httpResponseDTO.setResponseBody(swaggestContents);
        } else {
            httpResponseDTO.setResponseCode(404);
            httpResponseDTO.setResponseMessage("STATUS_404: User not found");
            httpResponseDTO.setResponseBody(new ArrayList<>());
        }
        return httpResponseDTO;
    }

    public HttpResponseDTO callFetchSwag(final String user) {
        try {
            String url = swagServiceUrl + "/swag/fetch-swag?user=" + user;
            ResponseEntity<HttpResponseDTO> responseEntity = restTemplate.getForEntity(url, HttpResponseDTO.class);
            return responseEntity.getBody();
        } catch (RestClientException ex) {
            throw new SwaggestServiceException(ex.getMessage(), ex);
        }
    }

    public HttpResponseDTO callFetchSwagger(final String user) {
        try {
            String url = swaggerServiceUrl + "/swagger/fetch-swagger?user=" + user;
            ResponseEntity<HttpResponseDTO> responseEntity = restTemplate.getForEntity(url, HttpResponseDTO.class);
            return responseEntity.getBody();
        } catch (RestClientException ex) {
            throw new SwaggestServiceException(ex.getMessage(), ex);
        }
    }
}
