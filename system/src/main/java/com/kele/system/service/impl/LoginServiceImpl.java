package com.kele.system.service.impl;

import com.kele.system.dao.SystemDao;
import com.kele.system.dao.dto.LoginUserDO;
import com.kele.system.service.LoginService;
import com.kele.system.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @description:
 * @author: dzy
 * @createDate: 2020/1/20 15:14
 * @version: 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    SystemDao systemDao;

    @Override
    public boolean toLogin(LoginVO loginVO) {
        //封装do对象
        LoginUserDO userDO = new LoginUserDO();
        userDO.setAccount(loginVO.getAccount());
        userDO.setPassword(loginVO.getPassword());
        //通过用户名密码查询数据库
        Optional<LoginUserDO> one = systemDao.findOne(Example.of(userDO));
        //判断是否存在数据
        if (one.isPresent()) {
            userDO = one.orElse(null);
            //TODO 在这里要将userDO放入redis并生成token 最好的方式是使用jwt
            return true;
        }
        return false;
    }
}
