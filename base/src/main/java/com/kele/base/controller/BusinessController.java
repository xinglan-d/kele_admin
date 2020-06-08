package com.kele.base.controller;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.model.annotation.base.*;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:业务基础层 20200607全部修改为aop代理具体实现参数请去 {@link com.kele.base.model.aspect.BusinessAspect} 查看
 * @author: dzy
 * @createDate: 2020/1/20 14:30
 * @version: 1.0
 */

@Log4j2
public abstract class BusinessController<V extends BusinessBaseVO<D>, D extends BusinessBaseDO> {


    private final BusinessServiceImpl<V, D> businessService = new BusinessServiceImpl(
            BusinessUtil.getTClassName(getClass(), 0),
            BusinessUtil.getTClassName(getClass(), 1)
    );

    @GetMapping("/getPageAttr")
    @PageAttr
    public Result<PageAttrVO> getPageAttr() {
        return null;
    }

    @PostMapping()
    @GetAspect
    public Result<PageData<V>> getAll(HttpServletRequest request, HttpServletResponse response, @RequestBody V vo) {
        return null;
    }

    @GetMapping("/getEditAttr")
    @EditAttr
    public Result<EditAttrVO> getEditAttr(String primaryKey) {
        return null;
    }

    @PostMapping("/edit")
    @EditAspect
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

    @DeleteMapping(value = "/del")
    @DelAspect
    public Result<EditAttrVO> del(String ids) {
        return null;
    }

    @GetMapping(value = "/select/{field}")
    @SelectAspect
    public Result<Selects> select(@PathVariable String field) {
        return null;
    }


}
