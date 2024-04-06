package swagger.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import swagger.entity.Swagger;

import java.util.List;


@Repository
public interface SwaggerRepository extends MongoRepository<Swagger, String> {
    List<Swagger> findByUser(String user);
}
