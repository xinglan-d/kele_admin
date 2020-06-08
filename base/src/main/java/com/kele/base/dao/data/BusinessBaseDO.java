package com.kele.base.dao.data;

import com.kele.base.util.BeanUtil;
import com.kele.base.util.BusinessUtil;
import lombok.extern.log4j.Log4j2;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 数据库查询默认的父类
 * @author: dzy
 * @createDate: 2020/1/20 14:53
 * @version: 1.0
 */
@Log4j2
public abstract class BusinessBaseDO implements Serializable {
    @Transient
    private List<Field> fields = BusinessUtil.getFields(this.getClass());
    @Transient
    private BeanUtil beanUtil = new BeanUtil(this);


    public Object getFieldValue(String columnName) {
        try {
            return beanUtil.getValue(columnName);
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public Object getFieldValue(List<String> columnNames) {
        String columnName = columnNames.get(0);
        if (columnNames.size() == 1) {
            return getFieldValue(columnName);
        }
        Object fieldValue = getFieldValue(columnName);
        if (List.class.isAssignableFrom(fieldValue.getClass())) {
            List list = new ArrayList();
            for (BusinessBaseDO businessBaseDO : (List<BusinessBaseDO>) fieldValue) {
                list.add(businessBaseDO.getFieldValue(columnNames.stream().skip(1).collect(Collectors.toList())));
            }
            return list;
        } else if (BusinessBaseDO.class.isAssignableFrom(fieldValue.getClass())) {
            return ((BusinessBaseDO) fieldValue).getFieldValue(columnNames.stream().skip(1).collect(Collectors.toList()));
        }
        return null;
    }

    /**
     * 设置字段值
     *
     * @param fieldName 字段名
     * @param value     值
     */
    public void setFieldValue(String fieldName, Object value) {
        Class filedClass = beanUtil.getFiledClass(fieldName);
        if (filedClass.isAssignableFrom(value.getClass())) {
            try {
                beanUtil.setValue(fieldName, value);
            } catch (InvocationTargetException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 设置字段值
     *
     * @param fieldNames 字段名称
     * @param value      价值
     * @throws Exception 异常
     */
    public void setFieldValue(List<String> fieldNames, Object value) throws Exception {
        for (String fieldName : fieldNames) {
            for (Field field : fields) {
                //先找到对应的字段
                if (fieldName.equals(field.getName())) {
                    //获取当前字段是否是list
                    if (field.getType().equals(List.class)) {
                        Class<?> aClass = BusinessUtil.getTClassName(field.getGenericType(), 0);
                        List valueList = (List) value;
                        if (valueList.size() == 0) {
                            return;
                        }
                        Class<?> vClass = valueList.get(0).getClass();
                        //如果值的类型相等直接放入
                        if (aClass.equals(vClass)) {
                            setFieldValue(fieldName, value);
                            return;
                        }
                        if (BusinessBaseDO.class.isAssignableFrom(aClass)) {
                            List list = new ArrayList();
                            for (Object o : valueList) {
                                BusinessBaseDO do1 = (BusinessBaseDO) aClass.newInstance();
                                do1.setFieldValue(fieldNames.stream().skip(1).collect(Collectors.toList()), o);
                                list.add(do1);
                            }
                            setFieldValue(field.getName(), list);

                        }

                    } else {
                        if (BusinessBaseDO.class.isAssignableFrom(field.getType())) {
                            BusinessBaseDO aDo = (BusinessBaseDO) field.getType().getDeclaredConstructor().newInstance();
                            aDo.setFieldValue(fieldNames.stream().skip(1).collect(Collectors.toList()), value);
                        } else {
                            setFieldValue(fieldName, value);
                        }
                    }
                    break;
                }
            }
            break;
        }

    }

    /**
     * 获取主键
     *
     * @return {@link Object}
     */
    public Object getPrimaryKey() {
        for (Field field : fields) {
            if (field.getAnnotation(Id.class) != null) {
                try {
                    return beanUtil.getValue(field.getName());
                } catch (InvocationTargetException | IllegalAccessException e) {
                    log.error("未找到do的id值", e);
                    break;
                }
            }
        }
        return null;
    }

    /**
     * 设置主键
     *
     * @param primaryKey 主键
     */
    public void setPrimaryKey(Object primaryKey) {
        for (Field field : fields) {
            if (field.getAnnotation(Id.class) != null) {
                try {
                    beanUtil.setValue(field.getName(), primaryKey);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    log.error("无法设置主键值", e);
                }
                return;
            }
        }
    }


}
