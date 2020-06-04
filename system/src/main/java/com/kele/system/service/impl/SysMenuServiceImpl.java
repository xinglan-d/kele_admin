package com.kele.system.service.impl;

import com.kele.base.model.enumerate.table.MenuEnum;
import com.kele.system.dao.MenuDao;
import com.kele.system.dao.ServiceMenuDao;
import com.kele.system.dao.dto.MenuDO;
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
    private final MenuDao menuDao;

    public SysMenuServiceImpl(ServiceMenuDao serviceMenuDao, MenuDao menuDao) {
        this.serviceMenuDao = serviceMenuDao;
        this.menuDao = menuDao;
    }


    @Override
    public List<ServiceVO> getServiceMenus() {
        return ServiceVO.createServices(serviceMenuDao.findAll());
    }

    @Override
    public List<ButtonVO> getPageButtons(String id) {
        MenuDO menuDO = new MenuDO();
        menuDO.setParentId(id);
        menuDO.setType(String.valueOf(MenuEnum.button.getType()));
        List<MenuDO> buttons = menuDao.findAll(Example.of(menuDO));
        return buttons.stream().map(ButtonVO::new).collect(Collectors.toList());
    }
}
