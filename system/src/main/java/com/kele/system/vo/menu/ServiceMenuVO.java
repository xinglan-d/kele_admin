package com.kele.system.vo.menu;

import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.edit.FormColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.system.dao.dto.ServiceDO;
import lombok.Data;

import java.util.List;

/**
 * @description:全部系统菜单
 * @author: duzongyue
 * @createDate: 2020/03/27 18:51
 * @version: 1.0
 */
@Data
public class ServiceMenuVO extends BusinessBaseVO<ServiceDO> {

    @BusinessColumn(value = "服务名")
    @TableColumn
    @FormColumn
    private String name;
    @BusinessColumn(value = "服务路径")
    @TableColumn
    private String servicePath;
    @BusinessColumn(value = "图标")
    @TableColumn
    private String icon;
    @BusinessColumn
    private List<MenuVO> menus;


}
