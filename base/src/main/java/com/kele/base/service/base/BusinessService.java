package com.kele.base.service.base;

import com.kele.base.vo.page.PageAttrVO;

/**
 * @description:基础业务服务层
 * @author: dzy
 * @createDate: 2020/1/20 14:51
 * @version: 1.0
 */

public interface BusinessService<V,D> {

    void getAll(int page,int size);

    void setVOClass(Class<V> voClass) ;
    void setDOClass(Class<D> doClass);

    PageAttrVO getPageAttr();
}
