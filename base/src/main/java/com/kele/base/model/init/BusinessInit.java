package com.kele.base.model.init;

import com.kele.base.model.bo.BusinessSysMenuBO;
import com.kele.base.model.common.Commons;
import com.kele.base.model.common.menu.Menu;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:业务系统初始化方法
 * @author: duzongyue
 * @createDate: 2020/02/06 02:20
 * @version: 1.0
 */
@Component
public class BusinessInit implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //获取系统内存在的菜单
        Commons.getInstance().setSysMenu(Menu.getInstance().getSysMenu());

    }
}
