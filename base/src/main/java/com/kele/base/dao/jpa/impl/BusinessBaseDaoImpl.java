package com.kele.base.dao.jpa.impl;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.dao.jpa.BusinessBaseDao;
import com.kele.base.dao.jpa.PageParameter;
import com.sun.xml.bind.v2.model.core.ID;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @description:业务查询数据实现层
 * @author: duzongyue
 * @createDate: 2020/03/22 00:50
 * @version: 1.0
 */
@Repository
@Getter
public class BusinessBaseDaoImpl<D extends BusinessBaseDO, ID> implements BusinessBaseDao<D, ID> {

    @PersistenceContext()
    private EntityManager entityManager;

    private Class<ID> idClass;

    private Class<D> doClass;

    @Override
    public List<D> findAll(PageParameter pageParameter) {
        StringBuffer sb = new StringBuffer();
        sb.append("from ");
        sb.append(doClass.getSimpleName());
        Query query = getEntityManager().createQuery(sb.toString());
        query.setFirstResult(pageParameter.getPageNumber());
        query.setMaxResults(pageParameter.getPageSize());
        return query.getResultList();
    }


    @Override
    public D findById(ID id) {
        return null;
    }

    @Override
    public void setIdClass(Class<ID> idClass) {
        this.idClass = idClass;
    }

    @Override
    public void setDoClass(Class<D> doClass) {
        this.doClass = doClass;
    }
}
