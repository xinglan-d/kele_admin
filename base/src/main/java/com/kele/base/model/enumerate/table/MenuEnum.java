package com.kele.base.model.enumerate.table;

import lombok.Getter;

@Getter
public enum MenuEnum implements SelectsEnum {

    menu(0, "一级菜单"),
    accessTableUrl(1, "通用表格页"),
    button(2, "按钮"),
    ;


    MenuEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    private Integer type;
    private String name;

    @Override
    public Object getId() {
        return type;
    }

    @Override
    public Object getPid() {
        return null;
    }

    @Override
    public Object getName() {
        return name;
    }
}
