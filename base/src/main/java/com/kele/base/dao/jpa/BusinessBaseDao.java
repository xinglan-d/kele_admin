package com.kele.base.dao.jpa;

import com.kele.base.dao.data.BusinessBaseDO;
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

    Integer getTotal(PageParameter pageParameter);

    List<D> findAll(PageParameter pageParameter);

    void save(D doData);

    D findById(ID id);

    void setIdClass(Class<ID> idType);

    void setDoClass(Class<D> doClass);

}
