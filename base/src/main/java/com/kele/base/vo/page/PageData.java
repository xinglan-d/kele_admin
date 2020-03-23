package com.kele.base.vo.page;

import lombok.Data;

import java.util.List;

/**
 * @description:页面数据封装
 * @author: duzongyue
 * @createDate: 2020/03/22 17:40
 * @version: 1.0
 */
@Data
public class PageData<VO> {

    private Integer total = 0;

    private List<VO> rows;
}
