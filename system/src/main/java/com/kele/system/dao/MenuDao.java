package com.kele.system.dao;

import com.kele.system.dao.dto.MenuDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDao extends JpaRepository<MenuDO,String> {
}
