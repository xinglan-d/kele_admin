package com.kele.base.vo;

import lombok.Data;

/**
 * @description:树列表
 * @author: duzongyue
 * @createDate: 2020/05/17 01:15
 * @version: 1.0
 */
@Data
public class SelectVO {

    public SelectVO(Object id, Object name) {
        this.id = id;
        this.name = name;
    }

    public SelectVO(Object id, Object pid, Object name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

    private Object id;

    private Object pid;

    private Object name;
}
