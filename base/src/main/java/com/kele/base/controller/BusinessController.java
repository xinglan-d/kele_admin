package com.kele.base.controller;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.service.ResultService;
import com.kele.base.service.base.impl.BusinessServiceImpl;
import com.kele.base.util.BusinessUtil;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.base.vo.Selects;
import com.kele.base.vo.page.EditAttrVO;
import com.kele.base.vo.page.PageAttrVO;
import com.kele.base.vo.page.PageData;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:业务基础层
 * @author: dzy
 * @createDate: 2020/1/20 14:30
 * @version: 1.0
 */

@Log4j2
public abstract class BusinessController<V extends BusinessBaseVO<D>, D extends BusinessBaseDO> {

    private final BusinessServiceImpl<V, D> businessService = new BusinessServiceImpl(
            BusinessUtil.getTClassName(getClass(), 0),
            BusinessUtil.getTClassName(getClass(), 1));

    @RequestMapping("/getPageAttr")
    public Result<PageAttrVO> getPageAttr() {
        PageAttrVO pageAttr = businessService.getPageAttr(getClassUrl());
        return ResultService.success(pageAttr);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public Result<PageData<V>> getAll(HttpServletRequest request, @RequestBody(required = false) V vo) {
        Object businessUser = request.getParameter("businessUser");
        PageData<V> pageData = businessService.getAll(vo);
        return ResultService.success(pageData);
    }

    @RequestMapping("/getEditAttr")
    public Result<EditAttrVO> getPageAttr(String primaryKey) throws Exception {
        EditAttrVO editAttr = businessService.getEditAttr(primaryKey);
        return ResultService.success(editAttr);
    }

    @RequestMapping("/edit")
    public Result<EditAttrVO> edit(@RequestBody V vo) throws Exception {
        if (StringUtils.isBlank(vo.getPrimaryKey())) {
            businessService.addVO(vo);
        } else {
            businessService.editVO(vo);
        }
        //添加或者修改数据以后执行的方法
        afterEdit(vo);
        return ResultService.success(null);
    }

    protected void afterEdit(V vo) {

    }

    ;

    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    public Result<EditAttrVO> del(String ids) {
        businessService.del(ids);
        return ResultService.success(null);
    }

    @RequestMapping(value = "/select/{field}", method = RequestMethod.GET)
    public Result<Selects> select(@PathVariable String field) {
        Selects selects = businessService.getSelects(field);
        return ResultService.success(selects);
    }

    public String getClassUrl() {
        RequestMapping annotation = this.getClass().getAnnotation(RequestMapping.class);
        if (annotation != null) {
            if (annotation.value() != null && annotation.value().length != 0) {
                return annotation.value()[0];
            }
        }
        return null;
    }

}
