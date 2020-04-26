package com.kele.base.vo.page;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:编辑页面参数
 * @author: duzongyue
 * @createDate: 2020/04/20 08:59
 * @version: 1.0
 */
@Data
public class EditAttrVO {

    List<FormColumnParam> columns = new ArrayList<>();

}
