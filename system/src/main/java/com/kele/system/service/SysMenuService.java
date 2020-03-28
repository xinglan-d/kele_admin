package com.kele.system.service;

import com.kele.system.vo.menu.SysServiceMenuVO;

import java.util.List;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/03/27 19:05
 * @version: 1.0
 */
public interface SysMenuService {

    List<SysServiceMenuVO> getServiceMenus();
}
