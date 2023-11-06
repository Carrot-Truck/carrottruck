package com.boyworld.carrot.domain.menu.repository.command;

import com.boyworld.carrot.domain.menu.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 메뉴 옵션 CUD 레포지토리
 *
 * @author 최영환
 */
@Repository
public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {
}
