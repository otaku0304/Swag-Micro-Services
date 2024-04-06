package swaggest.dto;


import lombok.Getter;
import lombok.Setter;
import swaggest.utils.Utility;

@Getter
@Setter
public class SwaggestDTO {
    private String swaggestContent;
    private String user;

    public boolean isEmpty() {
       return Utility.isNullOrEmpty(swaggestContent, user);
    }
}
