package swagger.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class HttpResponseDTO {
    private int responseCode;
    private String token;
    private String responseMessage;
    private Object responseBody;
}