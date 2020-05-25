package com.kele.base.vo.page;

import com.kele.base.model.enumerate.base.ColumnType;
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

}
