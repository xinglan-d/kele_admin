package com.kele.base.vo.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @description:返回list页面所需的字段值和按钮组
 * @author: dzy
 * @createDate: 2019/11/27 15:48
 * @version: 1.0
 */
@Getter
@Setter

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageAttrVO {

    //页面表单数据
    private List<TableColumnParam> columns;

}
