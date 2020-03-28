package com.kele.base.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.util.BeanUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @description:业务数据层
 * @author: dzy
 * @createDate: 2020/1/20 14:30
 * @version: 1.0
 */
@Getter
@Setter
public abstract class BusinessBaseVO<D extends BusinessBaseDO> {

    @JsonIgnore
    protected BeanUtil beanUtil = new BeanUtil(this);

    private String primaryKey;

    public void setDo(D doData) throws InvocationTargetException, IllegalAccessException {
        Field[] fields = this.getClass().getDeclaredFields();
        BeanUtil doBeanUtil = new BeanUtil(doData);
        for (Field field : fields) {
            String[] fieldName = getFieldName(field);
            Object value = getValue(doBeanUtil, fieldName);
            //将获取到的值放入vo对象中
            if (value != null) {
                //TODO 需要在这里添加判断是否获取的是array数据
                //不同的数据类型需要添加的方式不同
                beanUtil.setValue(field.getName(), value);
            }
        }
    }

    private Object getValue(BeanUtil doBeanUtil, String[] fieldName) throws InvocationTargetException, IllegalAccessException {
        return getValue(doBeanUtil, fieldName, 0);
    }

    private Object getValue(BeanUtil beanUtil, String[] fieldName, int i) throws InvocationTargetException, IllegalAccessException {
        if (i == fieldName.length - 1) {
            return beanUtil.getValue(fieldName[i]);
        }
        Object value = beanUtil.getValue(fieldName[i]);
        return getValue(new BeanUtil(value), fieldName, ++i);
    }

    /**
     * 获取字段的名字用于获取对应的值
     *
     * @param field
     * @return java.lang.String[]
     * @author duzongyue
     * @date 2020-03-28 02:02:32
     */
    private String[] getFieldName(Field field) {
        String fieldName;
        BusinessColumn businessColumn = field.getAnnotation(BusinessColumn.class);
        if (businessColumn != null && StringUtils.isNotBlank(businessColumn.columnName())) {
            fieldName = businessColumn.columnName();
        } else {
            fieldName = field.getName();
        }
        return fieldName.split("[.]");
    }
}
