package com.kele.base.dao.jpa;

import lombok.Data;

/**
 * @description:页面查询参数
 * @author: duzongyue
 * @createDate: 2020/03/22 00:44
 * @version: 1.0
 */
@Data
public class PageParameter {


    public PageParameter(int pageNumber, int pageSize) {
        this(pageNumber, pageSize, true);
    }

    /**
     * @param pageNumber 页码
     * @param pageSize   获取数量
     * @param isZero     是否将页码减1
     * @return
     * @author duzongyue
     * @date 2020-03-22 00:47:59
     */
    public PageParameter(int pageNumber, int pageSize, boolean isZero) {
        if (isZero) {
            pageNumber = pageNumber != 0 ? pageNumber - 1 : pageNumber;
        }
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    private int pageNumber;

    private int pageSize;

}
