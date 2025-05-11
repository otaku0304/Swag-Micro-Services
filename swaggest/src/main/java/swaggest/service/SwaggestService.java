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

@Service
@Slf4j
public class SwaggestService {
    @Value("${swag.url}")
    private String swagServiceUrl;
    @Value("${swagger.url}")
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
        if (swaggestList.isEmpty()) {
            return Utility.setResponseCodeAndMessageWithBody(httpResponseDTO, 404, "STATUS_404: User not found",new ArrayList<>());
        }
        List<String> swaggestContents = new ArrayList<>();
        for (Swaggest swaggest : swaggestList) {
            swaggestContents.add(swaggest.getSwaggestContent());
        }
        return Utility.setResponseCodeAndMessageWithBody(httpResponseDTO, 201, "STATUS_201",swaggestContents);
    }

    public HttpResponseDTO callFetchSwag(final String user) {
        String url = swagServiceUrl + "/swag/fetch-swag?user=" + user;
        try {
            ResponseEntity<HttpResponseDTO> responseEntity = restTemplate.getForEntity(url, HttpResponseDTO.class);
            return responseEntity.getBody();
        } catch (RestClientException ex) {
            throw new SwaggestServiceException(ex.getMessage(), ex);
        }
    }

    public HttpResponseDTO callFetchSwagger(final String user) {
        String url = swaggerServiceUrl + "/swagger/fetch-swagger?user=" + user;
        try {
            ResponseEntity<HttpResponseDTO> responseEntity = restTemplate.getForEntity(url, HttpResponseDTO.class);
            return responseEntity.getBody();
        } catch (RestClientException ex) {
            throw new SwaggestServiceException(ex.getMessage(), ex);
        }
    }
}
