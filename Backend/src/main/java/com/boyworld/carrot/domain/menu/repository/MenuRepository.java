package com.boyworld.carrot.domain.menu.repository;

import com.boyworld.carrot.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 메뉴 레포지토리
 *
 * @author 최영환
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
