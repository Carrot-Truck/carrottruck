package com.boyworld.carrot.api.controller.review.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WithdrawalRequest {

    private String email;

    private String password;

    private Long foodTruckId;

    @Builder
    public WithdrawalRequest(String email, String password, Long foodTruckId) {
        this.email = email;
        this.password = password;
        this.foodTruckId = foodTruckId;
    }
}
