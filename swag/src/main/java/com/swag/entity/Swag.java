package com.swag.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Swag {
    private String swagContent;
    private String user;
}
