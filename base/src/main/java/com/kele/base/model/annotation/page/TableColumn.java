package com.kele.base.model.annotation.page;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumn {


    //是否是多选框
    boolean isCheckbox() default false;

    //是否可以搜索
    boolean search() default false;
}
