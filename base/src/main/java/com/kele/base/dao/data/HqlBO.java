package com.kele.base.dao.data;

import com.kele.base.model.enumerate.base.SearchEnum;
import com.kele.base.util.BusinessUtil;
import lombok.Data;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import java.lang.reflect.Field;
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
            for (int i = 0; i < wheres.size(); i++) {
                HqlWhereBO where = wheres.get(i);
                query.setParameter(i, where.getValue());
            }
        }
        return query;
    }

    private String getTotalHql() {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(");
        sb.append(getDoIdFiled(doClass));
        sb.append(") from ");
        sb.append(doClass.getSimpleName());
        sb.append(getWhereStr());
        return sb.toString();
    }

    private String getDoIdFiled(Class doClass) {
        List<Field> fields = BusinessUtil.getFields(doClass);
        return fields.stream().filter(field -> field.getAnnotation(Id.class) != null).findAny().get().getName();
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
            for (int i = 0; i < wheres.size(); i++) {
                HqlWhereBO where = wheres.get(i);
                sb.append(where.getName());
                if (SearchEnum.eq.getValue() == where.getRule()) {
                    sb.append(" =");
                } else if (SearchEnum.ne.getValue() == where.getRule()) {
                    sb.append(" !=");
                } else if (SearchEnum.contain.getValue() == where.getRule()) {
                    sb.append(" like");
                    where.setValue("%" + where.getValue() + "%");
                } else if (SearchEnum.lt.getValue() == where.getRule()) {
                    sb.append(" <");
                } else if (SearchEnum.gt.getValue() == where.getRule()) {
                    sb.append(" >");
                } else if (SearchEnum.preContain.getValue() == where.getRule()) {
                    sb.append(" like ");
                    where.setValue("%" + where.getValue());
                } else if (SearchEnum.afterContain.getValue() == where.getRule()) {
                    sb.append(" like ");
                    where.setValue(where.getValue() + "%");
                }
                sb.append("?");
                sb.append(i);
                sb.append(" ");
//                sb.append(where.getParameterName());
            }
        }

        return sb.toString();
    }
}
