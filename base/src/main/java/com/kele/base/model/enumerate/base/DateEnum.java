package com.kele.base.model.enumerate.base;

import lombok.Getter;

/**
 * @author dzy
 */

@Getter
public enum DateEnum {

    DEFAULT(),
    DATE_TIME(LayDateType.DATE_TIME, DateFormat.LAY_DATE_TIME),
    CREATION_TIME(LayDateType.DATE_TIME, DateFormat.LAY_DATE_TIME);


    private LayDateType type;
    private DateFormat format;

    DateEnum() {
    }

    DateEnum(LayDateType type, DateFormat format) {
        this.type = type;
        this.format = format;
    }

}
