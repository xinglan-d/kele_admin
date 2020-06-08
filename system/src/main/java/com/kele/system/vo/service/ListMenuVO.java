package com.kele.system.vo.service;

import com.kele.system.dao.dto.MenuDO;
import lombok.Data;

import java.util.List;

/**
 * @description:一级菜单
 * @author: duzongyue
 * @createDate: 2020/05/31 13:36
 * @version: 1.0
 */
@Data
public class ListMenuVO {

    private String menuId;
    private String parentId;
    private String name;
    private String type;
    private String url;
    private String icon;
    private String color;
    private List<ListMenuVO> menus;


    public ListMenuVO(MenuDO menu, List<String> ids) {
        this.menuId = menu.getMenuId();
        this.parentId = menu.getParentId();
        this.name = menu.getName();
        this.type = menu.getType();
        this.url = menu.getUrl();
        this.icon = menu.getIcon();
        this.color = menu.getColor();
        this.menus = ServiceVO.addMenus(menu.getMenus(), ids);
    }
    public ListMenuVO(MenuDO menu) {
        this.menuId = menu.getMenuId();
        this.parentId = menu.getParentId();
        this.name = menu.getName();
        this.type = menu.getType();
        this.url = menu.getUrl();
        this.icon = menu.getIcon();
        this.color = menu.getColor();
        this.menus = ServiceVO.addMenus(menu.getMenus());
    }

}
