package com.kele.system.vo.menu;

import com.kele.base.vo.BusinessBaseVO;
import com.kele.system.dao.dto.SysServiceDO;
import lombok.Data;

import java.util.List;

/**
 * @description:全部系统菜单
 * @author: duzongyue
 * @createDate: 2020/03/27 18:51
 * @version: 1.0
 */
@Data
public class SysServiceMenuVO extends BusinessBaseVO<SysServiceDO> {

    private String serviceId;

    private String name;

    private String servicePath;

    private String icon;

    private List<SysMenuVO> menus;


}
