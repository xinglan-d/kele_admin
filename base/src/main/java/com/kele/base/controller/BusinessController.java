package com.kele.base.controller;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.service.ResultService;
import com.kele.base.service.base.BusinessService;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.base.vo.page.EditAttrVO;
import com.kele.base.vo.page.PageAttrVO;
import com.kele.base.vo.page.PageData;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

/**
 * @description:业务基础层
 * @author: dzy
 * @createDate: 2020/1/20 14:30
 * @version: 1.0
 */

@Log4j2
public class BusinessController<V extends BusinessBaseVO, D extends BusinessBaseDO> implements InitializingBean {

    @Autowired
    private BusinessService businessService;


    @RequestMapping("/getPageAttr")
    public Result<PageAttrVO> getPageAttr() {
        PageAttrVO pageAttr = businessService.getPageAttr();
        return ResultService.success(pageAttr);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public Result<PageData<V>> getAll(@RequestBody(required = false) V vo) {
        log.info("测试");
        PageData<V> pageData = businessService.getAll(vo);
        return ResultService.success(pageData);
    }

    @RequestMapping("/getEditAttr")
    public Result<EditAttrVO> getPageAttr(Object primaryKey) {
        EditAttrVO editAttr = businessService.getEditAttr();
        return ResultService.success(editAttr);
    }

    @RequestMapping("/edit")
    public Result<EditAttrVO> edit(@RequestBody V vo) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (StringUtils.isBlank(vo.getPrimaryKey())) {
            businessService.addVO(vo);
        } else {
            businessService.editVO(vo);
        }
        return ResultService.success(null);
    }

    private Class getTClassName(int order) {
        Class tClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[order];
        return tClass;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        businessService.setVOClass(getTClassName(0));
        businessService.setDOClass(getTClassName(1));
    }
}
