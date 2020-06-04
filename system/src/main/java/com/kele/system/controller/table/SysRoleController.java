package com.kele.system.controller.table;

import com.kele.base.controller.BusinessController;
import com.kele.base.controller.Result;
import com.kele.base.service.ResultService;
import com.kele.base.vo.SelectVO;
import com.kele.base.vo.Selects;
import com.kele.system.dao.dto.RoleDO;
import com.kele.system.service.SysMenuService;
import com.kele.system.vo.RoleVO;
import com.kele.system.vo.service.ServiceVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:部门控制层
 * @author: duzongyue
 * @createDate: 2020/05/09 11:42
 * @version: 1.0
 */

@RestController
@RequestMapping("/sysRole")
public class SysRoleController extends BusinessController<RoleVO, RoleDO> {

    private final SysMenuService sysMenuService;

    public SysRoleController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @GetMapping("/service/menu")
    public Result<Selects> serviceMenu(HttpServletRequest request) {
        List<ServiceVO> serviceMenus = sysMenuService.getServiceMenus();
        Selects selects = new Selects();
        serviceMenus.forEach(service -> {
            selects.getSelects().add(new SelectVO(service.getServiceId(), null, service.getName()));
            service.getMenus().forEach(menu -> {
                selects.getSelects().add(new SelectVO(menu.getMenuId(), menu.getParentId(), menu.getName()));
            });
        });
        return ResultService.success(selects);
    }

}
