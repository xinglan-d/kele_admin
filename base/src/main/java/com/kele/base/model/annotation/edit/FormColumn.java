package com.kele.base.model.annotation.edit;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormColumn {


    /**
     * 输入类型 可以去 https://github.com/yiminghe/async-validator 这个网址查看相关类型
     * string: Must be of type string. This is the default type.
     * number: Must be of type number.
     * boolean: Must be of type boolean.
     * method: Must be of type function.
     * regexp: Must be an instance of RegExp or a string that does not generate an exception when creating a new RegExp.
     * integer: Must be of type number and an integer.
     * float: Must be of type number and a floating point number.
     * array: Must be an array as determined by Array.isArray.
     * object: Must be of type object and not Array.isArray.
     * enum: Value must exist in the enum.
     * date: Value must be valid as determined by Date
     * url: Must be of type url.
     * hex: Must be of type hex.
     * email: Must be of type email.
     * any: Can be any type.
     */
    String type() default "";

    /**
     * 是否必需输入
     */
    boolean required() default false;

    /**
     * 检验规则
     */
    String rules() default "";

    boolean hide() default false;

    //最少输入字符 默认不限制
    int min() default -1;

    //最大输入字符 默认不限制
    int max() default -1;

    String pidField() default "";
}
