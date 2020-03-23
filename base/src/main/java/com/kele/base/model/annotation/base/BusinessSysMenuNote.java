package com.kele.base.model.annotation.base;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基础的系统菜单注解
 *
 * @return
 * @author duzongyue
 * @date 2020-02-10 14:20:42
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessSysMenuNote {

    String name();

    String icon() default "";
}
