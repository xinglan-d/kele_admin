package com.kele.base.model.common;

import com.kele.base.controller.BaseResult;

/**
 * @description:通用回复配置
 * @author: duzongyue
 * @createDate: 2020/02/17 02:03
 * @version: 1.0
 */
public class ResultCommon {

    public static final BaseResult SUCCESS = new BaseResult(200,"success");
    public static final BaseResult ERROR = new BaseResult(500,"error");
}
