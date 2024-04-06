package com.swag.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import com.swag.GlobalExceptionsHandler.SwaggestServiceException;
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
    @Value("${swaggest.service.url}")
    private String swaggestServiceUrl;
    @Value("${swagger.service.url}")
    private String swaggerServiceUrl;
    private final Firestore firestore;
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
            httpResponseDTO.setResponseCode(409);
            httpResponseDTO.setResponseMessage("Swag content already exists for this user.");
            return httpResponseDTO;
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
        httpResponseDTO.setResponseCode(201);
        httpResponseDTO.setResponseMessage("Swag content saved successfully.");
        httpResponseDTO.setResponseBody(swagDTO);
        return httpResponseDTO;
    }


    public HttpResponseDTO fetch(final String user) throws InterruptedException, ExecutionException {
        HttpResponseDTO httpResponseDTO = new HttpResponseDTO();
        Query query = firestore.collection(SwagConstant.SWAG_COLLECTION).whereEqualTo(SwagConstant.USER, user.toLowerCase());
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        if (!ObjectUtils.isEmpty(documents)) {
            List<String> swagContentList = new ArrayList<>();
            for (QueryDocumentSnapshot document : documents) {
                String swagContent = document.getString(SwagConstant.SWAG_CONTENT);
                swagContentList.add(swagContent);
            }
            httpResponseDTO.setResponseCode(201);
            httpResponseDTO.setResponseMessage("Swag fetched successfully");
            httpResponseDTO.setResponseBody(swagContentList);
        } else {
            httpResponseDTO.setResponseCode(404);
            httpResponseDTO.setResponseMessage("User not found");
        }
        return httpResponseDTO;
    }

    public HttpResponseDTO saveSwagger(SwagDTO swagDTO) {
        String url = swaggerServiceUrl + "/swagger/save";
        ResponseEntity<HttpResponseDTO> responseEntity = restTemplate.postForEntity(url, swagDTO, HttpResponseDTO.class);
        return responseEntity.getBody();
    }

    public HttpResponseDTO saveSwaggest(SwaggestDTO swaggestDTO) {
        try {
            log.info("swaggest-body:" + swaggestDTO);
            String url = swaggestServiceUrl + "/swaggest/save";
            ResponseEntity<HttpResponseDTO> responseEntity = restTemplate.postForEntity(url, swaggestDTO, HttpResponseDTO.class);
            log.info("swaggest-body:" + responseEntity);
            return responseEntity.getBody();
        } catch (Exception ex) {
            throw new SwaggestServiceException("Swaggest data is empty. Please send the data");
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
            httpResponseDTO.setResponseCode(200);
            httpResponseDTO.setResponseMessage("Swag content deleted successfully");
        } else {
            httpResponseDTO.setResponseCode(404);
            httpResponseDTO.setResponseMessage("No matching swag content found for deletion to the user");
        }
        return httpResponseDTO;
    }
}
