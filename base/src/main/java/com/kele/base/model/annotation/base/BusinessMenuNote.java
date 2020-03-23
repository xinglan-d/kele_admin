package com.kele.base.model.annotation.base;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基础列表扫描接口
 *
 * @return
 * @author duzongyue
 * @date 2020-02-06 02:08:02
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessMenuNote {

    //菜单名
    String name();

    //菜单图标
    String icon() default "";

    Class sysMenu();
}
