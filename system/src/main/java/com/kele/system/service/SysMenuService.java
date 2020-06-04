package com.kele.system.service;

import com.kele.system.vo.service.ButtonVO;
import com.kele.system.vo.service.ServiceVO;

import java.util.List;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/03/27 19:05
 * @version: 1.0
 */
public interface SysMenuService {

    List<ServiceVO> getServiceMenus();

    List<ButtonVO> getPageButtons(String id);
}
