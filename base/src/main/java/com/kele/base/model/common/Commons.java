package com.kele.base.model.common;

import com.kele.base.model.bo.BusinessSysMenuBO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @description:公共参数类
 * @author: duzongyue
 * @createDate: 2020/02/17 02:13
 * @version: 1.0
 */
@Getter
@Setter
public class Commons {
    //单例模式
    private static Commons common;

    //系统菜单
    private List<BusinessSysMenuBO> sysMenu;

    public static Commons getInstance() {
        if (common == null) {
            common = new Commons();
        }
        return common;
    }
}
