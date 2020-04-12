package com.kele.base.vo.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @description:
 * @author: dzy
 * @createDate: 2019/12/16 13:49
 * @version: 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableDateFormat {

    private String type;

    private String format;
}
