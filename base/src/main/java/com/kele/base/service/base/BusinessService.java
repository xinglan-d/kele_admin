package com.kele.base.service.base;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.base.vo.page.EditAttrVO;
import com.kele.base.vo.page.PageAttrVO;
import com.kele.base.vo.page.PageData;

import java.lang.reflect.InvocationTargetException;

/**
 * @description:基础业务服务层
 * @author: dzy
 * @createDate: 2020/1/20 14:51
 * @version: 1.0
 */

public interface BusinessService<V extends BusinessBaseVO, D extends BusinessBaseDO> {

    /**
     * 获取全部数据
     */
    PageData<V> getAll(V vo);

    void setVOClass(Class<V> voClass);

    void setDOClass(Class<D> doClass);

    /**
     * 获取列表参数
     */
    PageAttrVO getPageAttr();

    /**
     * 获取编辑页面参数
     */
    EditAttrVO getEditAttr(String primaryKey) throws InvocationTargetException, IllegalAccessException;

    void addVO(V vo) throws InstantiationException, IllegalAccessException, InvocationTargetException;

    void editVO(V vo) throws InvocationTargetException, IllegalAccessException;

    void del(String ids);
}
