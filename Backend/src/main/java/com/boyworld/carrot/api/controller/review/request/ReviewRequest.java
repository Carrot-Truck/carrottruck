package com.boyworld.carrot.api.controller.review.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ReviewRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long foodTruckId;

    @NotNull
    private Long orderId;

    @NotBlank
    private String content;

    @NotNull
    @Max(value = 5)
    private int grade;

    private MultipartFile image;

    @Builder
    public ReviewRequest(Long memberId, Long foodTruckId, Long orderId, String content, int grade, MultipartFile image){
        this.memberId = memberId;
        this.foodTruckId = foodTruckId;
        this.orderId = orderId;
        this.content = content;
        this.grade = grade;
        this.image = image;
    }

}
