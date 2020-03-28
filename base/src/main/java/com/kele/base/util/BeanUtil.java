package com.kele.base.util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @ClassName: BeanUtil
 * @Description: bean工具类
 * @author: dzy
 * @date: 2019/11/15 13:43
 * @version:1.0
 **/
@Getter
@Setter
@Log4j2
public class BeanUtil {

    private Class<?> aClass;

    Object data;

    private static Class[] EMPTY_PARAMETER_TYPES = new Class[0];

    public BeanUtil(Object data) {
        aClass = data.getClass();
        this.data = data;
    }

    public void setValue(String fieldName, Object... args) throws InvocationTargetException, IllegalAccessException {
        String setFieldName = "set" + capitalize(fieldName);
        Class[] class1 = getArgsType(args);
        Method method = getMethod(aClass, setFieldName, class1);
        if (method != null) {
            validateSetter(method);
            method.invoke(data, args);
        }
    }

    private void validateSetter(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new IllegalArgumentException("set方法 \"" + method + "\" 非 public");
        }
    }

    private Class[] getArgsType(Object[] args) {
        Class[] class1 = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            class1[i] = arg.getClass();
        }
        return class1;
    }

    //通过get方法获取属性值
    public Object getValue(String fieldName) throws InvocationTargetException, IllegalAccessException {
        String getFieldName = "get" + capitalize(fieldName);
        Method method = getMethod(aClass, getFieldName, EMPTY_PARAMETER_TYPES);
        if (method != null) {
            validateGetter(method);
            return method.invoke(data, EMPTY_PARAMETER_TYPES);
        }
        String isFieldName = "is" + capitalize(fieldName);
        method = getMethod(aClass, isFieldName, EMPTY_PARAMETER_TYPES);
        if (method != null) {
            validateGetter(method);
            return method.invoke(data, EMPTY_PARAMETER_TYPES);
        }
        return null;
    }

    /**
     * 验证get方法
     */
    private void validateGetter(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new IllegalArgumentException("get方法 \"" + method + "\" 非 public");
        }

        if (Void.TYPE.equals(method.getReturnType())) {
            throw new IllegalArgumentException("get方法 \"" + method + "\" 为 void");
        }
    }

    //获取方法 如果获取不到代表没有这个方法
    private Method getMethod(Class targetClass, String methodName, Class[] parameterTypes) {
        try {
            return targetClass.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    //将第一个字符转换位大写
    private String capitalize(String property) {
        return Character.toUpperCase(property.charAt(0)) +
                property.substring(1);
    }
}
