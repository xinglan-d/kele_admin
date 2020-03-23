package com.kele.base.model.common.menu;

import com.kele.base.model.annotation.base.BusinessMenuNote;
import com.kele.base.model.annotation.base.BusinessSysMenuNote;
import com.kele.base.model.bo.BusinessMenuBO;
import com.kele.base.model.bo.BusinessSysMenuBO;
import com.kele.base.model.util.ClassUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:菜单类方法
 * @author: duzongyue
 * @createDate: 2020/02/06 02:28
 * @version: 1.0
 */
public class Menu {

    private static final String MENU_PACKAGE_PATH = "com.kele";
    private static Menu menu;
    private Menu(){};
    public static Menu getInstance() {
        if (menu == null) {
            menu = new Menu();
        }
        return menu;
    }

    /**
     * 获取当前系统下的所以菜单
     *
     * @param
     * @return java.util.List<com.kele.base.model.bo.BusinessSysMenuBO>
     * @author duzongyue
     * @date 2020-02-24 15:28:32
     */
    public List<BusinessSysMenuBO>  getSysMenu() {
        List<BusinessSysMenuBO> businessSysMenuBOS = new ArrayList<>();
        List<Class<?>> classes = ClassUtil.getClasses(MENU_PACKAGE_PATH);
        for (Class<?> aClass : classes) {
            //将系统菜单获取并缓存
            BusinessSysMenuNote businessSysMenu = aClass.getAnnotation(BusinessSysMenuNote.class);
            if(businessSysMenu != null){
                BusinessSysMenuBO businessSysMenuBO = new BusinessSysMenuBO();
                businessSysMenuBO.setName(businessSysMenu.name());
                businessSysMenuBO.setIcon(businessSysMenu.icon());
                businessSysMenuBO.setAClass(aClass);
                businessSysMenuBOS.add(businessSysMenuBO);
            }

        }
        for (Class<?> aClass : classes) {
            //将一级菜单获取并缓存
            BusinessMenuNote businessMenu = aClass.getAnnotation(BusinessMenuNote.class);
            if(businessMenu != null ){
                for (BusinessSysMenuBO businessSysMenuBO : businessSysMenuBOS) {
                    if(businessSysMenuBO.getAClass() == businessMenu.sysMenu()){
                        BusinessMenuBO businessMenuBO = new BusinessMenuBO();
                        businessMenuBO.setName(businessMenu.name());
                        businessMenuBO.setIcon(businessMenu.icon());
                        businessMenuBO.setAClass(aClass);
                        businessSysMenuBO.getBusinessMenus().add(businessMenuBO);
                    }
                }

            }
        }
        return businessSysMenuBOS;
    }

    public static void main(String[] args) {
        Menu.getInstance().getSysMenu();
    }
}
