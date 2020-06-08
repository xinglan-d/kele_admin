package com.kele.system.dao;

import com.kele.system.dao.dto.DeptDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysDeptDao extends JpaRepository<DeptDO, String> {

    List<DeptDO> findAllBySeqIsNull();
}
