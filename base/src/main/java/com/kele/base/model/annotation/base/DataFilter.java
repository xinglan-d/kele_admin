package com.kele.base.model.annotation.base;

import com.kele.base.model.enumerate.base.FilterMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务字段过滤注解 通过此注解可以过滤获取到的数据
 * 过滤模式有2中
 *  1通过用户id进行过滤
 *  2通过部门的seq进行过滤
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataFilter {

    /**
     * 过滤模式
     *
     * @return {@link FilterMode}
     */
    FilterMode mode();


    /**
     * 过滤字段对应的do对象值
     *
     * @return {@link String}
     */
    String filterFiled();
}
