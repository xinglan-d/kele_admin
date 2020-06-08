package com.kele.base.service.base;

import com.kele.base.controller.BusinessInterface;
import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.base.vo.Selects;
import com.kele.base.vo.page.EditAttrVO;
import com.kele.base.vo.page.PageAttrVO;
import com.kele.base.vo.page.PageData;

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

    /**
     * 获取列表参数
     */
    PageAttrVO getPageAttr(String url);

    /**
     * 获取编辑页面参数
     */
    EditAttrVO getEditAttr(String primaryKey) throws Exception;

    void addVO(V vo) throws Exception;

    void editVO(V vo) throws Exception;

    void del(String ids);

    Selects getSelects(String field);

    void setBusinessInterface(BusinessInterface businessInterface);
}
