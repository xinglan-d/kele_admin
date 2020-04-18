package com.kele.system.service.impl;

import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.util.BusinessUtils;
import com.kele.system.dao.ServiceMenuDao;
import com.kele.system.dao.dto.SysServiceDO;
import com.kele.system.service.SysMenuService;
import com.kele.system.vo.menu.SysServiceMenuVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @description:系统菜单
 * @author: duzongyue
 * @createDate: 2020/03/27 19:09
 * @version: 1.0
 */
@Service
@Log4j2
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private ServiceMenuDao serviceMenuDao;


    @Override
    public List<SysServiceMenuVO> getServiceMenus() {
        List<SysServiceDO> serviceMenus = serviceMenuDao.findAll();
        BusinessUtils businessUtils = new BusinessUtils(SysServiceMenuVO.class);
        try {
            return businessUtils.dosToVos(serviceMenus, BusinessColumn.class);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
