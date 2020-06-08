package com.kele.system.vo;

import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.edit.FormColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.model.enumerate.base.ColumnType;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.system.dao.dto.DeptDO;
import lombok.Data;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/05/09 11:26
 * @version: 1.0
 */
@Data
public class DeptVO extends BusinessBaseVO<DeptDO> {

    @BusinessColumn(value = "部门名")
    @TableColumn()
    @FormColumn()
    private String deptName;

    @BusinessColumn(value = "父部门", type = ColumnType.TREE
            , url = "{service}/sysDept/tree")
    @TableColumn()
    @FormColumn()
    private String deptPid;
    @BusinessColumn(value = "seq")
    @TableColumn()
    private String seq;
}
