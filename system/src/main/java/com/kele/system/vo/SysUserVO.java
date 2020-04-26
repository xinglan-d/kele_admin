package com.kele.system.vo;

import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.edit.FormColumn;
import com.kele.base.model.annotation.page.TableColumn;
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
public class SysUserVO extends BusinessBaseVO<UserDO> {

    @BusinessColumn(value = "用户id")
    @TableColumn
    private String userId;

    @BusinessColumn(value = "账户")
    @TableColumn(search = false)
    @FormColumn(required = true, min = 3, max = 12)
    private String account;

    @BusinessColumn(value = "密码")
    @TableColumn(search = false)
    @FormColumn(required = true, max = 12)
    private String password;

    @BusinessColumn(value = "手机号")
    @TableColumn
    @FormColumn(required = true, min = 3)
    private String phone;

}
