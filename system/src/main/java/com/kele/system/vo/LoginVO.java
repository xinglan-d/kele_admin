package com.kele.system.vo;

import com.kele.base.vo.BusinessBaseVO;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: dzy
 * @createDate: 2020/1/20 14:28
 * @version: 1.0
 */
@Getter
@Setter
public class LoginVO extends BusinessBaseVO {

    //账号
    private String account;
    //密码
    private String password;

    private String code;
}
