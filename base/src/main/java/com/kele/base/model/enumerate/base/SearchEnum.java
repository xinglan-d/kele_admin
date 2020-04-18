package com.kele.base.model.enumerate.base;

import lombok.Getter;

/**
 * 查询规则
 *
 * @author duzongyue
 * @date 2020-04-15 06:35:43
 */
@Getter
public enum SearchEnum {

    /**
     * 小于
     */
    lt(0),
    /**
     * 等于
     */
    eq(1),
    /**
     * 不等于
     */
    ne(2),
    /**
     * 大于
     */
    gt(3),
    /**
     * 包含
     */
    contain(4);

    SearchEnum(int value) {
        this.value = value;
    }

    private int value;
}
