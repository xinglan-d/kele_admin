package com.kele.base.model.enumerate.table;

public enum ButtonEnum implements SelectsEnum {

    add(0, "新增"),
    edit(1, "编辑"),
    del(2, "删除"),
    delAll(3, "删除所有"),
    jump_id(4,"跳转带id")
    ;


    ButtonEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    private int type;
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
