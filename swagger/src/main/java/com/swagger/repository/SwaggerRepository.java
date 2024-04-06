package com.swagger.repository;

import com.swagger.entity.Swagger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SwaggerRepository extends MongoRepository<Swagger, String> {
    List<Swagger> findByUser(String user);
}
