package com.kele.system.vo.service;

import com.kele.system.dao.dto.MenuDO;
import lombok.Data;

/**
 * @description:按钮列表
 * @author: duzongyue
 * @createDate: 2020/05/31 13:38
 * @version: 1.0
 */
@Data
public class ButtonVO {

    private String menuId;
    private String parentId;
    private String name;
    private String type;
    private String url;
    private String icon;
    private String color;
    private Integer buttonType;

    public ButtonVO(MenuDO menu) {
        this.menuId = menu.getMenuId();
        this.parentId = menu.getParentId();
        this.name = menu.getName();
        this.type = menu.getType();
        this.url = menu.getUrl();
        this.icon = menu.getIcon();
        this.color = menu.getColor();
        this.buttonType = menu.getButtonType();
    }
}
