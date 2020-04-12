package com.kele.base.controller;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.model.util.SpringUtil;
import com.kele.base.service.ResultService;
import com.kele.base.service.base.BusinessService;
import com.kele.base.service.base.impl.BusinessServiceImpl;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.base.vo.page.PageAttrVO;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.ParameterizedType;

/**
 * @description:业务基础层
 * @author: dzy
 * @createDate: 2020/1/20 14:30
 * @version: 1.0
 */

public class BusinessController<V extends BusinessBaseVO, D extends BusinessBaseDO> {

    private BusinessService businessService;


    @RequestMapping("/getPageAttr")
    public Result<PageAttrVO> getPageAttr() {
        PageAttrVO pageAttr = getBusinessService().getPageAttr();
        return ResultService.success(pageAttr);
    }

    @RequestMapping("/getAll")
    public void getAll() {
        getBusinessService().getAll(1, 10);
    }

    private Class getTClassName(int order) {
        Class tClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[order];
        return tClass;
    }

    public BusinessService getBusinessService() {
        if (businessService == null) {
            businessService = (BusinessService) SpringUtil.getBean(BusinessServiceImpl.class);
            businessService.setVOClass(getTClassName(0));
            businessService.setDOClass(getTClassName(1));
        }
        return businessService;
    }
}
