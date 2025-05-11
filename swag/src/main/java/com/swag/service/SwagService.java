package com.swag.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.swag.dto.SwaggerDTO;
import com.swag.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.swag.globalexceptionshandler.SwagServiceException;
import com.swag.converter.SwagConverter;
import com.swag.dto.HttpResponseDTO;
import com.swag.dto.SwagDTO;
import com.swag.dto.SwaggestDTO;
import com.swag.utils.SwagConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class SwagService {
    @Value("${swaggest.appName}")
    private String swaggestServiceAppName;
    @Value("${swagger.url}")
    private String swaggerServiceUrl;
    private final Firestore firestore;
    @LoadBalanced
    private final RestTemplate restTemplate;

    public SwagService(Firestore firestore, RestTemplateBuilder restTemplateBuilder) {
        this.firestore = firestore;
        this.restTemplate = restTemplateBuilder.build();
    }

    public HttpResponseDTO save(final SwagDTO swagDTO) throws InterruptedException, ExecutionException {
        HttpResponseDTO httpResponseDTO = new HttpResponseDTO();
        String swagContent = swagDTO.getSwagContent().toLowerCase();
        String user = swagDTO.getUser().toLowerCase();
        Query query = firestore.collection(SwagConstant.SWAG_COLLECTION)
                .whereEqualTo(SwagConstant.SWAG_CONTENT, swagContent)
                .whereEqualTo(SwagConstant.USER, user);
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        if (!querySnapshotApiFuture.get().isEmpty()) {
            return Utility.setResponseCodeAndMessage(httpResponseDTO, 409, "Swag content already exists for this user.");
        }
        swagDTO.setSwagContent(swagContent);
        swagDTO.setUser(user);
        String documentId = UUID.randomUUID().toString();
        DocumentReference documentReference = firestore.collection(SwagConstant.SWAG_COLLECTION).document(documentId);
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(
                SwagConverter.convertSwagDTOtoEntity(swagDTO),
                SetOptions.merge()
        );
        writeResultApiFuture.get();
        return Utility.setResponseCodeAndMessageWithBody(httpResponseDTO, 201, "Swag content saved successfully",swagDTO);
    }


    public HttpResponseDTO fetch(final String user) throws InterruptedException, ExecutionException {
        HttpResponseDTO httpResponseDTO = new HttpResponseDTO();
        Query query = firestore.collection(SwagConstant.SWAG_COLLECTION).whereEqualTo(SwagConstant.USER, user.toLowerCase());
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        if (ObjectUtils.isEmpty(documents)) {
            return Utility.setResponseCodeAndMessage(httpResponseDTO, 404, "STATUS_404: User not found");
        }
        List<String> swagContentList = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            String swagContent = document.getString(SwagConstant.SWAG_CONTENT);
            swagContentList.add(swagContent);
        }
        return Utility.setResponseCodeAndMessageWithBody(httpResponseDTO, 201, "Swag fetched successfully",swagContentList);
    }

    public HttpResponseDTO saveSwagger(final SwaggerDTO swaggerDTO) {
        String url = swaggerServiceUrl + "/swagger/save";
        try {
            ResponseEntity<HttpResponseDTO> responseEntity = restTemplate.postForEntity(url, swaggerDTO, HttpResponseDTO.class);
            return responseEntity.getBody();
        } catch (RestClientException ex) {
            throw new SwagServiceException(ex.getMessage(), ex);
        }
    }

    public HttpResponseDTO saveSwaggest(final SwaggestDTO swaggestDTO) {
        String url = swaggestServiceAppName + "/swaggest/save";
        try {
            ResponseEntity<HttpResponseDTO> responseEntity = restTemplate.postForEntity(url, swaggestDTO, HttpResponseDTO.class);
            return responseEntity.getBody();
        } catch (RestClientException ex) {
            throw new SwagServiceException(ex.getMessage(), ex);
        }
    }

    public HttpResponseDTO deleteSwagForUser(final SwagDTO swagDTO) throws InterruptedException, ExecutionException {
        HttpResponseDTO httpResponseDTO = new HttpResponseDTO();
        CollectionReference swagCollection = firestore.collection(SwagConstant.SWAG_COLLECTION);
        Query query = swagCollection.whereEqualTo(SwagConstant.USER, swagDTO.getUser().toLowerCase()).whereEqualTo(SwagConstant.SWAG_CONTENT, swagDTO.getSwagContent().toLowerCase());
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();
        List<WriteResult> results = new ArrayList<>();
        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            results.add(document.getReference().delete().get());
        }
        if (!ObjectUtils.isEmpty(results)) {
            return Utility.setResponseCodeAndMessage(httpResponseDTO, 200, "Swag content deleted successfully");
        } else {
            return Utility.setResponseCodeAndMessage(httpResponseDTO, 404, "No matching swag content found for deletion to the user");
        }
    }
}
