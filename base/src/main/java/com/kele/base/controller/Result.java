package com.kele.base.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description:统一返回值
 * @author: dzy
 * @createDate: 2019/11/27 16:38
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    private int code;

    private String msg;

    private T data;
}
