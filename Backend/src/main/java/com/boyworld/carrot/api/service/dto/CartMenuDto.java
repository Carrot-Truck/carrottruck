package com.boyworld.carrot.api.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartMenuDto {
    @NotNull
    private String name;

}
