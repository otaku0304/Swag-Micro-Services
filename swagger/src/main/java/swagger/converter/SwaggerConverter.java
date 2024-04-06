package swagger.converter;

import swagger.dto.SwaggerDTO;
import swagger.entity.Swagger;
import swagger.utils.Utility;

public class SwaggerConverter {

    private SwaggerConverter() {
        throw new IllegalStateException("SwaggerConverter class");
    }

    public static Swagger convertSwaggerDTOtoEntity(final SwaggerDTO swaggerDTO) {
        return Swagger.builder()
                .swaggerContent(swaggerDTO.getSwaggerContent())
                .user(swaggerDTO.getUser())
                .genericDetails(Utility.createGenericDetails(swaggerDTO.getUser()))
                .build();
    }
}
