package com.kele.system.vo;

import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.edit.FormColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.system.dao.dto.SysDeptDO;
import lombok.Data;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/05/09 11:26
 * @version: 1.0
 */
@Data
public class SysDeptVO extends BusinessBaseVO<SysDeptDO> {

    @BusinessColumn(value = "部门名")
    @TableColumn()
    @FormColumn()
    private String deptName;

    @BusinessColumn(value = "父部门")
    @TableColumn()
    @FormColumn()
    private String deptPid;

}
