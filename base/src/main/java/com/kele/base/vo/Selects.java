package com.kele.base.vo;

import com.kele.base.dao.data.BusinessBaseDO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/05/25 13:37
 * @version: 1.0
 */
@Data
public class Selects {

    public Selects() {

    }

    public Selects(List<BusinessBaseDO> selects, String idField, String nameField) {
        for (BusinessBaseDO select : selects) {
            this.selects.add(new SelectVO(select.getFieldValue(idField), select.getFieldValue(nameField)));
        }
    }


    List<SelectVO> selects = new ArrayList<>();
}
