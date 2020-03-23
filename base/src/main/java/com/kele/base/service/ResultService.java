package com.kele.base.service;

import com.kele.base.controller.BaseResult;
import com.kele.base.controller.Result;
import com.kele.base.controller.ResultInterface;
import com.kele.base.model.common.ResultCommon;
import com.kele.base.model.enumerate.controller.ResultEnum;

/**
 * @description:
 * @author: dzy
 * @createDate: 2020/1/20 15:55
 * @version: 1.0
 */
public class ResultService {

    public static <T> Result<T> success(T data) {
        return result(ResultCommon.SUCCESS, data);
    }

    public static <T> Result<T> error(T data) {
        return result(ResultCommon.ERROR, data);
    }

    public static <T> Result<T> result(ResultInterface resultInterface) {
        return result(resultInterface, null);
    }

    public static <T> Result<T> result(ResultInterface resultInterface, T data) {
        BaseResult baseResult = resultInterface.getBaseResult();
        return result(baseResult.getCode(), baseResult.getMsg(), data);
    }

    public static <T> Result<T> result(int code, String msg, T data) {
        return new Result<T>(code, msg, data);
    }
}
