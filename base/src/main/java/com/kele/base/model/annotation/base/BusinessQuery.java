package com.kele.base.model.annotation.base;

import com.kele.base.model.enumerate.base.QueryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessQuery {

    String value() default "";

    QueryType type() default QueryType.HQL;

    String idField() default "id";

    String pidField() default "pid";

    String nameField() default "name";

    Class menu() default BusinessQuery.class;

}
