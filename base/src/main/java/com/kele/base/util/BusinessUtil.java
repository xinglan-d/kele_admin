package com.kele.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:业务工具类
 * @author: duzongyue
 * @createDate: 2020/05/28 14:27
 * @version: 1.0
 */
public class BusinessUtil {

    /**
     * 获取泛系的类
     *
     * @param order 订单
     * @return {@link Class}
     */
    public static Class<?> getTClassName(Type type, int order) {
        return getTClassName((ParameterizedType) type, order);
    }

    public static Class<?> getTClassName(Class aClass, int order) {
        return getTClassName(((ParameterizedType) aClass.getGenericSuperclass()), order);
    }

    /**
     * 获取泛系的类
     *
     * @param pt    pt
     * @param order 订单
     * @return {@link Class<?>}
     */
    public static Class<?> getTClassName(ParameterizedType pt, int order) {
        return (Class<?>) pt.getActualTypeArguments()[order];
    }

    /**
     * 获取当前vo和父vo的变量
     *
     * @param
     * @return java.util.List<java.lang.reflect.Field>
     * @author duzongyue
     * @date 2020-04-05 10:52:22
     */
    public static List<Field> getFields(Class clazz) {
        List<Field> list = new ArrayList<>();
        if (clazz == null) {
            return list;
        }
        list.addAll(getFields(clazz.getSuperclass()));
        list.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
        return list;
    }

}
