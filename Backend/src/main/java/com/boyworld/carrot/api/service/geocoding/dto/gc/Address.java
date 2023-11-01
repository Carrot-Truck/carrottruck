package com.boyworld.carrot.api.service.geocoding.dto.gc;

import lombok.Getter;

@Getter
public class Address {
    private String name;
    private Code code;
    private Region region;
    private Land land;
}

