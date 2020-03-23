package com.kele.system.controller;

import com.kele.base.controller.Result;
import com.kele.base.model.bo.BusinessSysMenuBO;
import com.kele.base.model.common.Commons;
import com.kele.base.service.ResultService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:返回系统菜单
 * @author: duzongyue
 * @createDate: 2020/02/17 01:41
 * @version: 1.0
 */
@RestController
public class SysMenuController {

    @RequestMapping("/getSysMenu")
    public Result<?> getSysMenu(){
        List<BusinessSysMenuBO> sysMenu = Commons.getInstance().getSysMenu();
        return ResultService.success(sysMenu);
    }

}
