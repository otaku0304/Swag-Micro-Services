package com.swag.converter;

import com.swag.dto.SwagDTO;
import com.swag.entity.Swag;

public class SwagConverter {

    private SwagConverter() {
        throw new IllegalStateException("SwagConverter class");
    }

    public static Swag convertSwagDTOtoEntity(final SwagDTO swagDTO) {
        return Swag.builder()
                .swagContent(swagDTO.getSwagContent())
                .user(swagDTO.getUser())
                .build();

    }
}
