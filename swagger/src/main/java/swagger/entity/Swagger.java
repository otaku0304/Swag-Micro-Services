package swagger.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "save-swagger")
public class Swagger implements Serializable {
    @Id
    private String id;
    private String swaggerContent;
    private String user;
    private GenericDetails genericDetails;
}
