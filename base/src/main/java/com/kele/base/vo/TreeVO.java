package com.kele.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description:树列表
 * @author: duzongyue
 * @createDate: 2020/05/17 01:15
 * @version: 1.0
 */
@Data
@AllArgsConstructor
public class TreeVO {

    private String id;

    private String pid;

    private String name;
}
