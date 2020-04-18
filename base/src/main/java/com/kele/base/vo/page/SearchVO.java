package com.kele.base.vo.page;

import com.kele.base.model.enumerate.base.SearchEnum;
import lombok.Data;

/**
 * @description:页面传入的查询参数
 * @author: duzongyue
 * @createDate: 2020/04/15 06:33
 * @version: 1.0
 */
@Data
public class SearchVO {

    //查询的字段名
    private String name;

    //查询的字段对象
    private Object value;

    //规则
    private Integer rule = SearchEnum.eq.getValue();

    //字段类型
    private Class aClass;
}
