package com.kele.base.vo.page;

import lombok.Data;

/**
 * @description: 编辑页面的
 * @author: duzongyue
 * @createDate: 2020/04/20 09:01
 * @version: 1.0
 */
@Data
public class FormColumnParam {

    //变量名
    private String field;

    //显示名字
    private String title;

    //值
    private Object value;

    //日期格式化参数
    private TableDateFormat format;

    //字段类型
    private String type;

    //验证规则
    private RulesVO rules;

    //是否隐藏
    private boolean hide;

    //获取数据接口
    private String url;

    //是否支持多选
    private boolean multiple;

    //是否保存上层页面传递过来的值
    private String pidField;

}
