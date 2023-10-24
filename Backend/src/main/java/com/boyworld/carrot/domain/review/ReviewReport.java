package com.boyworld.carrot.domain.review;

import com.boyworld.carrot.domain.TimeBaseEntity;
import com.boyworld.carrot.domain.member.Member;
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
 * Review-Report Entity
 *
 * @author Gunhoo Park
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review_report")
public class ReviewReport extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_report_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(columnDefinition = "text", nullable = false)
    private String reason;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    private Boolean active;

    @Builder
    private ReviewReport(Member member, Review review, String reason, Status status,
        LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean active) {
        this.member = member;
        this.review = review;
        this.reason = reason;
        this.status = status;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.active = active;
    }
}
