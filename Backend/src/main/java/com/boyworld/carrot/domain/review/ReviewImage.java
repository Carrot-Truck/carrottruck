package com.boyworld.carrot.domain.review;

import com.boyworld.carrot.domain.TimeBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Review-Image Entity
 *
 * @author Gunhoo Park
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review_image")
public class ReviewImage extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(nullable = false, length = 255)
    private String uploadFileName;

    @Column(nullable = false, length = 255)
    private String saveFileName;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    private Boolean active;

    @Builder
    private ReviewImage(Review review, String uploadFileName, String saveFileName,
        LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean active) {
        this.review = review;
        this.uploadFileName = uploadFileName;
        this.saveFileName = saveFileName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.active = active;
    }
}
