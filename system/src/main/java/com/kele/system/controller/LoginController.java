package com.kele.system.controller;

import com.kele.base.controller.Result;
import com.kele.base.service.ResultService;
import com.kele.system.service.LoginService;
import com.kele.system.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/toLogin", method = RequestMethod.POST)
    private Result<?> toLogin(@RequestBody LoginVO loginVO) {
        if (loginService.toLogin(loginVO)) {
            return ResultService.success("123");
        } else {
            return ResultService.error("123");
        }
    }
}
