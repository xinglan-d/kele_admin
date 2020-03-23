package com.kele.base.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:系统菜单
 * @author: duzongyue
 * @createDate: 2020/02/10 15:08
 * @version: 1.0
 */
@Data
@JsonIgnoreProperties("aClass")
public class BusinessSysMenuBO {

    private String name;

    private String icon;

    @JsonIgnore
    private Class aClass;

    private List<BusinessMenuBO> businessMenus = new ArrayList<>();

}
