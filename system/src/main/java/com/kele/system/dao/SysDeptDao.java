package com.kele.system.dao;

import com.kele.system.dao.dto.SysDeptDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysDeptDao extends JpaRepository<SysDeptDO, String> {
}
