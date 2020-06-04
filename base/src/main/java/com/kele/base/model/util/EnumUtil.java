package com.kele.base.model.util;

import com.kele.base.model.enumerate.table.ButtonEnum;
import com.kele.base.model.enumerate.table.SelectsEnum;
import com.kele.base.vo.SelectVO;
import com.kele.base.vo.Selects;

import java.util.Arrays;

/**
 * @description:枚举工具类
 * @author: duzongyue
 * @createDate: 2020/05/29 15:55
 * @version: 1.0
 */
public class EnumUtil {

    public static Selects enumTransferSelects(Class<? extends SelectsEnum> selectsEnum) {
        Selects selects = new Selects();
        SelectsEnum[] enumConstants = selectsEnum.getEnumConstants();
        Arrays.stream(enumConstants).forEach(enumAttr -> {
            selects.getSelects().add(new SelectVO(enumAttr.getId(), enumAttr.getName()));
        });
        return selects;
    }

    public static void main(String[] args) {
        System.out.println(enumTransferSelects(ButtonEnum.class));
    }
}
