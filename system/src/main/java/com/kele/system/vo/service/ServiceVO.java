package com.kele.system.vo.service;

import com.kele.system.dao.dto.MenuDO;
import com.kele.system.dao.dto.ServiceDO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:服务列表
 * @author: duzongyue
 * @createDate: 2020/05/31 13:34
 * @version: 1.0
 */
@Data
public class ServiceVO {

    private String serviceId;
    //服务名
    private String name;
    //服务路径
    private String servicePath;
    //图标
    private String icon;
    //菜单
    private List<ListMenuVO> menus;


    public ServiceVO(ServiceDO serviceDO) {
        this.serviceId = serviceDO.getServiceId();
        this.name = serviceDO.getName();
        this.servicePath = serviceDO.getServicePath();
        this.icon = serviceDO.getIcon();
    }

    public static List<ServiceVO> createServices(List<ServiceDO> serviceDOS) {
        List<ServiceVO> list = new ArrayList<>();
        serviceDOS.forEach(serviceDO -> {
            ServiceVO serviceVO = new ServiceVO(serviceDO);
            serviceVO.setMenus(addMenus(serviceDO.getMenus()));
            list.add(serviceVO);
        });
        return list;
    }


    public static List<ServiceVO> createServices(List<ServiceDO> serviceDOS, List<String> ids) {
        List<ServiceVO> list = new ArrayList<>();
        serviceDOS.forEach(serviceDO -> {
            ServiceVO serviceVO = new ServiceVO(serviceDO);
            serviceVO.setMenus(addMenus(serviceDO.getMenus(), ids));
            list.add(serviceVO);
        });
        return list;
    }

    public static List<ListMenuVO> addMenus(List<MenuDO> menus) {
        return menus.stream().map(ListMenuVO::new).collect(Collectors.toList());
    }

    public static List<ListMenuVO> addMenus(List<MenuDO> menus, List<String> ids) {
        return menus.stream().filter(menu -> ids.contains(menu.getMenuId()))
                .map(menu -> new ListMenuVO(menu, ids))
                .collect(Collectors.toList());
    }
}
