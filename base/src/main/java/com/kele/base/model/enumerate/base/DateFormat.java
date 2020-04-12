package com.kele.base.model.enumerate.base;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum DateFormat {


    LAY_DATE_TIME("yyyy/MM/dd HH:mm:ss");


    private String format;

    DateFormat(String format) {
        this.format = format;
    }


}
