package com.kele.system.vo;

import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.system.dao.dto.UserDO;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/02/24 16:09
 * @version: 1.0
 */
public class SysUserVO extends BusinessBaseVO<UserDO> {

    @BusinessColumn(value = "用户id")
    @TableColumn
    private String userId;

    @BusinessColumn(value = "账户")
    @TableColumn
    private String account;

    @BusinessColumn(value = "密码")
    @TableColumn
    private String password;

    @BusinessColumn(value = "手机号")
    @TableColumn
    private String phone;

}
