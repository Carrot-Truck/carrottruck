package com.boyworld.carrot.api.service.fcm.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FCMNotificationRequestDto {
    private Long foodTruckId;
    private String title;
    private String body;
    private Map<String, String> data;

    @Builder
    public FCMNotificationRequestDto(Long foodTruckId, String title, String body, Map<String, String> data) {
        this.foodTruckId = foodTruckId;
        this.title = title;
        this.body = body;
        this.data = data;
    }
}
