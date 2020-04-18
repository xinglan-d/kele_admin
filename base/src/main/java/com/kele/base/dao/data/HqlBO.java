package com.kele.base.dao.data;

import com.kele.base.model.enumerate.base.SearchEnum;
import lombok.Data;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @description:hql语句的包装对象
 * @author: duzongyue
 * @createDate: 2020/04/16 11:28
 * @version: 1.0
 */
@Data
public class HqlBO {

    //对应的do类
    private Class doClass;

    //where条件
    private List<HqlWhereBO> wheres;


    public Query getQuery(EntityManager entityManager) {
        return setParameter(getStrHql(), entityManager);
    }

    public Query getTotalQuery(EntityManager entityManager) {
        return setParameter(getTotalHql(), entityManager);
    }

    private Query setParameter(String hql, EntityManager entityManager) {
        Query query = entityManager.createQuery(hql);
        if (wheres != null && wheres.size() != 0) {
            for (HqlWhereBO where : wheres) {
                query.setParameter(where.getName(), where.getValue());
            }
        }
        return query;
    }

    private String getTotalHql() {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(*) from ");
        sb.append(doClass.getSimpleName());
        sb.append(getWhereStr());
        return sb.toString();
    }

    private String getStrHql() {
        StringBuffer sb = new StringBuffer();
        sb.append(" from ");
        sb.append(doClass.getSimpleName());
        sb.append(getWhereStr());
        return sb.toString();
    }


    public String getWhereStr() {
        StringBuffer sb = new StringBuffer();
        if (wheres != null && wheres.size() != 0) {
            sb.append(" where ");
            for (HqlWhereBO where : wheres) {
                sb.append(where.getName());
                if (SearchEnum.eq.getValue() == where.getRule()) {
                    sb.append(" = ");
                } else if (SearchEnum.ne.getValue() == where.getRule()) {
                    sb.append(" != ");
                } else if (SearchEnum.contain.getValue() == where.getRule()) {
                    sb.append(" like ");
                    where.setValue("%" + where.getValue() + "%");
                } else if (SearchEnum.lt.getValue() == where.getRule()) {
                    sb.append(" < ");
                } else if (SearchEnum.gt.getValue() == where.getRule()) {
                    sb.append(" > ");
                }
                sb.append(" :");
                sb.append(where.getName());
            }
        }

        return sb.toString();
    }
}
