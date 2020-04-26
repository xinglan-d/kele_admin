package com.kele.base.vo.page;

import lombok.Data;

/**
 * @description:表单验证规则
 * @author: duzongyue
 * @createDate: 2020/04/22 10:12
 * @version: 1.0
 */
@Data
public class RulesVO {

    //检验类型
    private String type;

    //是否必须输入
    private boolean required;

    //最少输入字符
    private Integer min;

    //最多输入字符
    private Integer max;
}
