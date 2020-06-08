package com.kele.base.dao.data;

import lombok.Data;

import java.lang.reflect.Type;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/04/16 11:30
 * @version: 1.0
 */
@Data
public class HqlWhereBO {

    private String name;

    private String parameterName;

    private Object value;

    private Integer rule;

    private Type type;
}
