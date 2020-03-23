package com.kele.system.vo;

import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.vo.BusinessBaseVO;

import javax.persistence.*;
import java.util.Objects;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/02/24 16:09
 * @version: 1.0
 */
public class SysUserVO extends BusinessBaseVO {

    @BusinessColumn(value = "用户id")
    private String userId;

    @BusinessColumn(value = "账户")
    private String account;

    @BusinessColumn(value = "密码")
    private String password;

    @BusinessColumn(value = "手机号")
    private String phone;

}
