package com.kele.base.vo.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @description:页面表格参数
 * @author: dzy
 * @createDate: 2019/12/16 13:46
 * @version: 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableColumnParam {

    //是否是多选框
    private boolean checkbox;

    //变量名
    private String field;

    //多级变量名
    private String columnName;

    //显示名字
    private String title;

    //日期格式化参数
    private TableDateFormat format;

    //TODO 下拉选Code
    private String selectCode;

    //是否可以被搜索
    private boolean isSearch;
}
