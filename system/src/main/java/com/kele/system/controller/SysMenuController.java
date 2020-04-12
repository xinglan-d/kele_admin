package com.kele.system.controller;

import com.kele.base.controller.Result;
import com.kele.base.service.ResultService;
import com.kele.system.service.SysMenuService;
import com.kele.system.vo.menu.SysServiceMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private SysMenuService sysMenuService;


    @GetMapping("/getSysMenu")
    public Result<?> getSysMenu() {
        List<SysServiceMenuVO> serviceMenus = sysMenuService.getServiceMenus();
        return ResultService.success(serviceMenus);
    }

}
