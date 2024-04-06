package swaggest.converter;

import swaggest.dto.SwaggestDTO;
import swaggest.entity.Swaggest;
import swaggest.utils.Utility;

public class SwaggestConverter {
    private SwaggestConverter() {
        throw new IllegalStateException("SwaggestConverter class");
    }

    public static Swaggest convertSwagDTOtoEntity(final SwaggestDTO swaggestDTO) {
        return Swaggest.builder()
                .swaggestContent(swaggestDTO.getSwaggestContent())
                .user(swaggestDTO.getUser())
                .genericDetails(Utility.createGenericDetails(swaggestDTO.getUser()))
                .build();
    }
}
