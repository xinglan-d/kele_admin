package com.kele.base.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Transient;

/**
 * @description:系统内的2级菜单
 * @author: duzongyue
 * @createDate: 2020/02/10 15:09
 * @version: 1.0
 */
@Data
@JsonIgnoreProperties("aClass")
public class BusinessMenuBO {

    private String name;

    private String icon;

    @JsonIgnore
    private Class aClass;
}
