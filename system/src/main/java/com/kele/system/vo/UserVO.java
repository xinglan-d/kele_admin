package com.kele.system.vo;

import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.base.BusinessQuery;
import com.kele.base.model.annotation.edit.FormColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.model.enumerate.base.ColumnType;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.system.dao.dto.UserDO;
import lombok.Data;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/02/24 16:09
 * @version: 1.0
 */
@Data
public class UserVO extends BusinessBaseVO<UserDO> {

    @BusinessColumn(value = "用户id")
    private String userId;

    @BusinessColumn(value = "账户")
    @TableColumn(search = true)
    @FormColumn(required = true, min = 3, max = 12)
    private String account;

    @BusinessColumn(value = "密码")
    @TableColumn(search = true)
    @FormColumn(required = true, max = 12)
    private String password;

    @BusinessColumn(value = "手机号")
    @TableColumn(search = true)
    @FormColumn(required = true, min = 3)
    private String phone;
    @BusinessColumn(value = "seq",columnName = "dept.seq")
    @TableColumn(search = true)
    private String seq;

    @BusinessColumn(value = "所属部门", columnName = "deptId", type = ColumnType.TREE
            , url = "/{service}/sysDept/tree")
    @TableColumn
    @FormColumn(required = true)
    private String deptName;

    @BusinessColumn(value = "所属角色", columnName = "roleId", type = ColumnType.SELECT)
    @TableColumn
    @FormColumn(required = true)
    @BusinessQuery(value = "FROM RoleDO", idField = "roleId", nameField = "roleName")
    private String roleName;

}
