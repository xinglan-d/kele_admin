package com.kele.base.service.base;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.base.vo.page.PageAttrVO;
import com.kele.base.vo.page.PageData;

/**
 * @description:基础业务服务层
 * @author: dzy
 * @createDate: 2020/1/20 14:51
 * @version: 1.0
 */

public interface BusinessService<V extends BusinessBaseVO, D extends BusinessBaseDO> {

    PageData<V> getAll(V vo);

    void setVOClass(Class<V> voClass);

    void setDOClass(Class<D> doClass);

    PageAttrVO getPageAttr();
}
