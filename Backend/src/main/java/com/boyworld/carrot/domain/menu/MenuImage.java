package com.boyworld.carrot.domain.menu;

import com.boyworld.carrot.domain.TimeBaseEntity;
import com.boyworld.carrot.file.UploadFile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 메뉴 이미지 엔티티
 *
 * @author 최영환
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuImage extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_image_id")
    private Long id;

    @Embedded
    private UploadFile uploadFile;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false)
    private Boolean active;

    @Builder
    private MenuImage(UploadFile uploadFile, Menu menu, Boolean active) {
        this.uploadFile = uploadFile;
        this.menu = menu;
        this.active = active;
    }

    // == business logic == //
    public void deActivate() {
        this.active = false;
    }
}
