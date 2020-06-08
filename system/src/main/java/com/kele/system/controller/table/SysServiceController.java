package com.kele.system.controller.table;

import com.kele.base.controller.BusinessController;
import com.kele.base.controller.Result;
import com.kele.base.model.enumerate.table.MenuEnum;
import com.kele.base.service.ResultService;
import com.kele.system.dao.dto.ServiceDO;
import com.kele.system.service.SysMenuService;
import com.kele.system.vo.menu.ServiceMenuVO;
import com.kele.system.vo.service.ButtonVO;
import com.kele.system.vo.service.ListMenuVO;
import com.kele.system.vo.service.ServiceVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:用户控制层
 * @author: duzongyue
 * @createDate: 2020/02/24 15:39
 * @version: 1.0
 */
@RestController
@RequestMapping("/sysService")
public class SysServiceController extends BusinessController<ServiceMenuVO, ServiceDO> {

    private final SysMenuService sysMenuService;

    public SysServiceController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @GetMapping("/getService")
    public Result<List<ServiceVO>> getService(HttpServletRequest request) {
        List<ServiceVO> serviceMenus = sysMenuService.getServiceMenus();
        serviceMenus.forEach(service -> {
            service.setMenus(filterButton(service.getMenus()));
        });
        return ResultService.success(serviceMenus);
    }

    private List<ListMenuVO> filterButton(List<ListMenuVO> menus) {
        menus.forEach(menu -> {
            menu.setMenus(filterButton(menu.getMenus()));
        });
        return menus.stream().filter(menu -> !menu.getType().equals(MenuEnum.button.getType().toString())).collect(Collectors.toList());
    }

    @GetMapping("/getService/{id}")
    public Result<List<ButtonVO>> getPageButtons(@PathVariable String id) {
        return ResultService.success(sysMenuService.getPageButtons(id));
    }

}
