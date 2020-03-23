package com.kele.system.service.impl;

import com.kele.base.dao.jpa.BusinessBaseDao;
import com.kele.system.dao.dto.UserDO;
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


    @Override
    public boolean toLogin(LoginVO loginVO) {
        return true;
    }
}
