package com.boyworld.carrot.domain.menu.repository.command;

import com.boyworld.carrot.domain.menu.MenuImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 메뉴 이미지 CUD 레포지토리
 *
 * @autor 최영환
 */
@Repository
public interface MenuImageRepository extends JpaRepository<MenuImage, Long> {
}
