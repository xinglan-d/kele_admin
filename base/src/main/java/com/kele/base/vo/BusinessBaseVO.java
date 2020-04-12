package com.kele.base.vo;


import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.model.util.BusinessUtils;
import com.kele.base.util.BeanUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:业务数据层
 * @author: dzy
 * @createDate: 2020/1/20 14:30
 * @version: 1.0
 */
@Getter
@Setter
public abstract class BusinessBaseVO<D extends BusinessBaseDO> {

    @TableColumn(isCheckbox = true)
    protected String primaryKey;

    public void setDo(D doData) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        //获取vo的数据集
        BeanUtil beanUtil = new BeanUtil(this);
        //获取所有变量
        List<Field> fields = getFields();
        //获取do的数据集
        BeanUtil doBeanUtil = new BeanUtil(doData);
        //遍历vo变量
        for (Field field : fields) {
            //获取对应的变量名注解 有注解的用注解优先无注解的使用变量名
            String[] fieldName = getFieldName(field);
            //获取对应的变量值
            Object value = getValue(doBeanUtil, fieldName);

            /*if (field.getGenericType() instanceof BusinessBaseVO) {
                if (field.getType().isAssignableFrom(List.class)) {
                    BusinessUtils businessUtils = new BusinessUtils(field.getType());
                    value = businessUtils.dosToVos((List) value);
                }
            }*/
            //将获取到的值放入vo对象中
            if (value != null) {
                //TODO 需要在这里添加判断是否获取的是array数据
                if (field.getType().isAssignableFrom(List.class)) {
                    System.out.println(field.getType());
                    List list = (List) value;
                    Class<?>[] classes = getParameterizedType(field);
                    for (Class<?> aClass : classes) {
                        BusinessUtils businessUtils = new BusinessUtils(aClass);
                        value = businessUtils.dosToVos(list);
                    }
                    beanUtil.setValues(field.getName(), value);
                } else {
                    beanUtil.setValue(field.getName(), value);
                }

            }
        }
    }

    /**
     * 获取当前vo和父vo的变量
     *
     * @param
     * @return java.util.List<java.lang.reflect.Field>
     * @author duzongyue
     * @date 2020-04-05 10:52:22
     */
    public List<Field> getFields() {
        return getFields(this.getClass());
    }

    public List<Field> getFields(Class clazz) {
        List<Field> list = new ArrayList<>();
        if (clazz == null) {
            return list;
        }
        list.addAll(getFields(clazz.getSuperclass()));
        list.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
        return list;
    }

    /**
     * 获取do对应的数据集
     *
     * @param doBeanUtil
     * @param fieldName
     * @return java.lang.Object
     * @author duzongyue
     * @date 2020-04-05 10:54:51
     */
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


    /**
     * 获取某一个字段上面的泛型参数数组,典型的就是获取List对象里面是啥参数
     *
     * @param f
     * @return
     */
    public static Class<?>[] getParameterizedType(Field f) {
        // 获取f字段的通用类型
        Type fc = f.getGenericType(); // 关键的地方得到其Generic的类型
        // 如果不为空并且是泛型参数的类型
        if (fc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) fc;
            Type[] types = pt.getActualTypeArguments();
            if (types != null && types.length > 0) {
                Class<?>[] classes = new Class<?>[types.length];
                for (int i = 0; i < classes.length; i++) {
                    classes[i] = (Class<?>) types[i];
                }
                return classes;
            }
        }
        return new Class[0];
    }
}
