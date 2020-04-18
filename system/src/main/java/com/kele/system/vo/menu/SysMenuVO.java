package com.kele.system.vo.menu;

import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.system.dao.dto.SysMenuDO;
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
public class SysMenuVO extends BusinessBaseVO<SysMenuDO> {

    @BusinessColumn
    private String name;
    @BusinessColumn
    private String type;
    @BusinessColumn
    private String url;
    @BusinessColumn
    private String icon;
    @BusinessColumn
    private String color;
    @BusinessColumn
    private List<SysMenuVO> menus;

}
