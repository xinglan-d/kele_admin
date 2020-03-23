package com.kele.base.dao.jpa;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.vo.BusinessBaseVO;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:基础dao
 * @author: duzongyue
 * @createDate: 2020/02/24 16:34
 * @version: 1.0
 */
@Repository
public interface BusinessBaseDao<D extends BusinessBaseDO, ID> {

    List<D> findAll(PageParameter pageParameter);

    D findById(ID id);

    void setIdClass(Class<ID> idType);

    void setDoClass(Class<D> doClass);
}
