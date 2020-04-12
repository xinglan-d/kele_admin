package com.kele.base.model.enumerate.base;

import lombok.Getter;

@Getter
public enum LayDateType {

    YEAR("year"),//只提供年列表选择
    MONTH("month"),//只提供年、月选择
    DATE("date"),//可选择：年、月、日。type默认值，一般可不填
    TIME("time"),//只提供时、分、秒选择
    DATE_TIME("datetime"),//可选择：年、月、日、时、分、秒
    ;
    private String type;

    LayDateType(String type) {
        this.type = type;
    }

}
