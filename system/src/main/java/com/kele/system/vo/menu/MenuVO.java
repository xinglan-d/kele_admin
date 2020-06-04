package com.kele.system.vo.menu;

import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.base.BusinessQuery;
import com.kele.base.model.annotation.edit.FormColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.model.enumerate.base.ColumnType;
import com.kele.base.model.enumerate.base.QueryType;
import com.kele.base.model.enumerate.table.ButtonEnum;
import com.kele.base.model.enumerate.table.MenuEnum;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.system.dao.dto.MenuDO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/03/30 07:49
 * @version: 1.0
 */
@Getter
@Setter
public class MenuVO extends BusinessBaseVO<MenuDO> {
    @BusinessColumn
    private String menuId;
    @BusinessColumn(value = "所属服务", type = ColumnType.SELECT, url = "/{service}/sysRole/service/menu")
    @TableColumn(filterField = "pid", hide = true)
    @FormColumn(pidField = "pid", hide = true)
//    @BusinessQuery(value = "from ServiceDO", idField = "serviceId", nameField = "name")
    private String parentId;
    @BusinessColumn("菜单名")
    @TableColumn
    @FormColumn
    private String name;
    @BusinessColumn(value = "类型", type = ColumnType.SELECT)
    @TableColumn
    @FormColumn
    @BusinessQuery(type = QueryType.EMNU, menu = MenuEnum.class, idField = "type")
    private String type;
    @BusinessColumn("访问路径")
    @TableColumn
    @FormColumn()
    private String url;
    @BusinessColumn("菜单图标")
    @TableColumn
    @FormColumn
    private String icon;
    @BusinessColumn("颜色")
    @TableColumn
    @FormColumn
    private String color;
    @BusinessColumn(value = "按钮类型", type = ColumnType.SELECT)
    @TableColumn
    @FormColumn
    @BusinessQuery(type = QueryType.EMNU, menu = ButtonEnum.class, idField = "type")
    private Integer buttonType;

    @BusinessColumn
    private List<MenuVO> menus;

}
