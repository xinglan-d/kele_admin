package com.kele.system.service.impl;

import com.kele.base.model.enumerate.table.MenuEnum;
import com.kele.base.model.sys.UserInfo;
import com.kele.base.model.util.UserUtil;
import com.kele.system.dao.MenuDao;
import com.kele.system.dao.ServiceMenuDao;
import com.kele.system.dao.SysRoleAuthDao;
import com.kele.system.dao.dto.MenuDO;
import com.kele.system.dao.dto.RoleAuthDO;
import com.kele.system.dao.dto.ServiceDO;
import com.kele.system.dao.dto.UserDO;
import com.kele.system.service.SysMenuService;
import com.kele.system.vo.service.ButtonVO;
import com.kele.system.vo.service.ServiceVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:系统菜单
 * @author: duzongyue
 * @createDate: 2020/03/27 19:09
 * @version: 1.0
 */
@Service
@Log4j2
public class SysMenuServiceImpl implements SysMenuService {

    private final ServiceMenuDao serviceMenuDao;
    private final SysRoleAuthDao roleAuthDao;
    private final MenuDao menuDao;

    public SysMenuServiceImpl(ServiceMenuDao serviceMenuDao, SysRoleAuthDao roleAuthDao, MenuDao menuDao) {
        this.serviceMenuDao = serviceMenuDao;
        this.roleAuthDao = roleAuthDao;
        this.menuDao = menuDao;
    }


    @Override
    public List<ServiceVO> getServiceMenus() {
        UserInfo userInfo = UserUtil.getUserInfo();
        String roleId = ((UserDO) userInfo.getUser()).getRoleId();
        if (userInfo.isAdmin()) {
            return ServiceVO.createServices(serviceMenuDao.findAll());
        }
        RoleAuthDO roleAuthDO = new RoleAuthDO();
        roleAuthDO.setRoleId(roleId);
        //获取角色权限
        List<RoleAuthDO> roleAuths = roleAuthDao.findAll(Example.of(roleAuthDO));
        //获取menuId
        List<String> menuIds = roleAuths.stream().map(RoleAuthDO::getMenuId).collect(Collectors.toList());
        //通过menuId获取服务
        List<ServiceDO> services = serviceMenuDao.findAllById(menuIds);
        //通过角色的权限构建页面元素
        return ServiceVO.createServices(services, menuIds);
    }

    @Override
    public List<ButtonVO> getPageButtons(String id) {
        UserInfo userInfo = UserUtil.getUserInfo();
        MenuDO menuDO = new MenuDO();
        menuDO.setParentId(id);
        menuDO.setType(String.valueOf(MenuEnum.button.getType()));
        List<MenuDO> buttons = menuDao.findAll(Example.of(menuDO));
        if (userInfo.isAdmin()) {
            return buttons.stream().map(ButtonVO::new).collect(Collectors.toList());
        }

        String roleId = ((UserDO) userInfo.getUser()).getRoleId();
        RoleAuthDO roleAuthDO = new RoleAuthDO();
        roleAuthDO.setRoleId(roleId);
        //获取角色权限
        List<RoleAuthDO> roleAuths = roleAuthDao.findAll(Example.of(roleAuthDO));
        //获取menuId
        List<String> menuIds = roleAuths.stream().map(RoleAuthDO::getMenuId).collect(Collectors.toList());
        List<MenuDO> collect = buttons.stream().filter(button ->
                menuIds.contains(button.getMenuId())
        ).collect(Collectors.toList());
        return collect.stream().map(ButtonVO::new).collect(Collectors.toList());
    }

    @Override
    public List<ServiceVO> getRoleAuthServiceMenus() {


        return null;
    }
}
