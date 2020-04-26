package com.kele.base.dao.jpa.impl;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.dao.data.HqlBO;
import com.kele.base.dao.data.HqlWhereBO;
import com.kele.base.dao.jpa.BusinessBaseDao;
import com.kele.base.dao.jpa.PageParameter;
import com.kele.base.vo.page.SearchVO;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:业务查询数据实现层
 * @author: duzongyue
 * @createDate: 2020/03/22 00:50
 * @version: 1.0
 */
@Repository
@Getter
@Transactional
public class BusinessBaseDaoImpl<D extends BusinessBaseDO, ID> implements BusinessBaseDao<D, ID> {

    @PersistenceContext()
    private EntityManager entityManager;

    private Class<ID> idClass;

    private Class<D> doClass;


    @Override
    public Integer getTotal(PageParameter pageParameter) {
        HqlBO hqlBO = getFindHql(pageParameter);
        Query query = hqlBO.getTotalQuery(getEntityManager());
        Integer total = ((Long) query.getSingleResult()).intValue();
        return total;
    }

    //拼接实际的查询参数
    @Override
    public List<D> findAll(PageParameter pageParameter) {
        //TODO 目前存在的问题没有解决数据库注入的问题
        HqlBO hqlBO = getFindHql(pageParameter);
        Query query = hqlBO.getQuery(getEntityManager());
        query.setFirstResult(pageParameter.getPageNumber());
        query.setMaxResults(pageParameter.getPageSize());
        return query.getResultList();
    }

    @Override
    public void save(D doData) {
        getEntityManager().persist(doData);
    }


    private HqlBO getFindHql(PageParameter pageParameter) {
        HqlBO hqlBO = new HqlBO();
        hqlBO.setDoClass(doClass);
        hqlBO.setWheres(getWhereHql(pageParameter));
        return hqlBO;
    }

    /**
     * 拼接where语句
     *
     * @param pageParameter
     * @return java.lang.String
     * @author duzongyue
     * @date 2020-04-15 08:26:56
     */
    private List<HqlWhereBO> getWhereHql(PageParameter pageParameter) {
        List<HqlWhereBO> list = new ArrayList<>();
        for (SearchVO search : pageParameter.getSearch()) {
            Object value = search.getValue();
            if (value == null || value.equals("")) {
                continue;
            }
            HqlWhereBO whereBO = new HqlWhereBO();
            whereBO.setName(search.getName());
            whereBO.setRule(search.getRule());
            whereBO.setValue(value);
            list.add(whereBO);
        }
        return list;
    }

    /**
     * 居于不同的数据类型返回不同的值
     *
     * @param value
     * @param aClass
     * @return java.lang.String
     * @author duzongyue
     * @date 2020-04-15 07:55:05
     */
    private String getTypeValue(Object value, Class aClass) {
        if (aClass == String.class) {
            return "'" + value + "'";
        } else {
            return String.valueOf(value);
        }
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
