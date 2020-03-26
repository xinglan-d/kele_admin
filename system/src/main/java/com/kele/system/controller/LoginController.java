package com.kele.system.controller;

import com.kele.base.controller.Result;
import com.kele.base.service.ResultService;
import com.kele.system.service.LoginService;
import com.kele.system.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @description:登录控制层
 * @author: dzy
 * @createDate: 2020/1/20 14:54
 * @version: 1.0
 */
@RestController
@CrossOrigin
public class LoginController {


    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/toLogin", method = RequestMethod.POST)
    private Result<?> toLogin(@RequestBody(required = false) LoginVO loginVO) {
        if (loginService.toLogin(loginVO)) {
            return ResultService.success("123");
        } else {
            return ResultService.error("用户名密码错误");
        }
    }
}
