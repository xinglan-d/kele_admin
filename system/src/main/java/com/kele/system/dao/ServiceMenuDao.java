package com.kele.system.dao;

import com.kele.system.dao.dto.SysServiceDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceMenuDao extends JpaRepository<SysServiceDO,String> {
}
