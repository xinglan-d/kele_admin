package com.kele.system.vo;

import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.base.DataFilter;
import com.kele.base.model.annotation.edit.FormColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.model.enumerate.base.ColumnType;
import com.kele.base.model.enumerate.base.FilterMode;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.system.dao.dto.RoleDO;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/05/09 11:26
 * @version: 1.0
 */
@Data
@DataFilter(mode = FilterMode.DEPT, filterFiled = "deptSeq")
public class RoleVO extends BusinessBaseVO<RoleDO> {

    @BusinessColumn(value = "角色名")
    @TableColumn()
    @FormColumn()
    private String roleName;
    @BusinessColumn(value = "seq")
    @TableColumn()
    private String deptSeq;

    @BusinessColumn(value = "数据权限", columnName = "roleAuths.menuId", multiple = true, type = ColumnType.TREE,
            url = "{service}/sysRole/service/menu")
    @FormColumn()
    private List<String> authority;

}
