package com.kele.base.model.enumerate.base;

import lombok.Getter;

@Getter
public enum ColumnType {

    INPUT("input"),
    DATE("date"),
    SELECT("select"),
    TREE("tree"),
    UPLOAD("upload"),
    ;
    private String type;

    ColumnType(String type) {
        this.type = type;
    }
}
