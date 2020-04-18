package com.kele.base.dao.jpa;

import com.kele.base.vo.page.SearchVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:页面查询参数
 * @author: duzongyue
 * @createDate: 2020/03/22 00:44
 * @version: 1.0
 */
@Data
public class PageParameter {

    private int pageNumber;

    private int pageSize;

    List<SearchVO> search;

    public PageParameter() {
    }

    public PageParameter(Integer pageNumber, Integer pageSize) {
        this( pageNumber,  pageSize,true);
    }

    /**
     * @param pageNumber 页码
     * @param pageSize   获取数量
     * @param isZero     是否将页码减1
     * @return
     * @author duzongyue
     * @date 2020-03-22 00:47:59
     */
    public PageParameter(Integer pageNumber, Integer pageSize, boolean isZero) {
        if (isZero && pageNumber != null & pageNumber >= 1) {
            pageNumber = pageNumber - 1;
        }
        setPageNumber(pageNumber);
        setPageSize(pageSize);
        setSearch(null);
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public void setPageNumber(Integer pageNumber) {
        if (pageNumber == null) {
            pageNumber = 0;
        }
        this.pageNumber = pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            //TODO 回头修改为在数据库中配置
            pageSize = 10;
        }
        this.pageSize = pageSize;
    }

    public void setSearch(List<SearchVO> search) {
        if (search == null) {
            search = new ArrayList<>();
        }
        this.search = search;
    }
}
