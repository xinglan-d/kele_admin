package com.kele.system.dao;

import com.kele.system.dao.dto.RoleAuthDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleAuthDao extends JpaRepository<RoleAuthDO, String> {
}
