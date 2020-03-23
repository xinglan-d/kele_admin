package com.kele.base.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:通用返回值
 * @author: duzongyue
 * @createDate: 2020/02/17 01:57
 * @version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class BaseResult implements ResultInterface {

    private int code;

    private String msg;

    @Override
    public BaseResult getBaseResult() {
        return this;
    }
}
