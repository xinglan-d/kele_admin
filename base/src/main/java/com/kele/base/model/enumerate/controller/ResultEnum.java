package com.kele.base.model.enumerate.controller;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(200, "success"),
    ERROR(500, "error")
    ;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;
}
