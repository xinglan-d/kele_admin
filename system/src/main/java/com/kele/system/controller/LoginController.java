package com.kele.system.controller;

import com.kele.base.controller.Result;
import com.kele.base.service.ResultService;
import com.kele.system.service.LoginService;
import com.kele.system.vo.LoginVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:登录控制层
 * @author: dzy
 * @createDate: 2020/1/20 14:54
 * @version: 1.0
 */
@RestController
public class LoginController {


    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/toLogin", method = RequestMethod.POST)
    private Result<?> toLogin(@RequestBody(required = false) LoginVO loginVO) {
        String s = loginService.toLogin(loginVO);
        if (s != null) {
            return ResultService.success(s);
        } else {
            return ResultService.error("用户名密码错误");
        }
    }
}
