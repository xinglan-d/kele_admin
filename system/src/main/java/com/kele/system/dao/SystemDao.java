package com.kele.system.dao;

import com.kele.system.dao.dto.LoginUserDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemDao extends JpaRepository<LoginUserDO,String> {
}
