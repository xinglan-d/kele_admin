package com.kele.system.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kele.base.util.JwtUtil;
import com.kele.base.util.RedisCacheUtil;
import com.kele.system.dao.SystemDao;
import com.kele.system.dao.dto.UserDO;
import com.kele.system.service.LoginService;
import com.kele.system.vo.LoginVO;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: dzy
 * @createDate: 2020/1/20 15:14
 * @version: 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final String ADMIN_USER_PREFIX = "admin_";

    private final SystemDao systemDao;
    private final RedisCacheUtil redisUtil;
    private ObjectMapper mapper = new ObjectMapper();

    public LoginServiceImpl(SystemDao systemDao, RedisCacheUtil redisUtil) {
        this.systemDao = systemDao;
        this.redisUtil = redisUtil;
    }

    @Override
    public String toLogin(LoginVO loginVO) {
        //封装do对象
        UserDO userDO = new UserDO();
        userDO.setAccount(loginVO.getAccount());
        userDO.setPassword(loginVO.getPassword());
        //通过用户名密码查询数据库
        Optional<UserDO> one = systemDao.findOne(Example.of(userDO));
        //判断是否存在数据
        if (one.isPresent()) {
            try {
                userDO = one.get();
                userDO.setRole(null);
                String userId = userDO.getUserId();
                String userJson = mapper.writeValueAsString(userDO);
                //将用户的信息放入到redis中
                redisUtil.setCacheObject(ADMIN_USER_PREFIX + userId, userJson, 30, TimeUnit.MINUTES);
                return new JwtUtil().createToken(userId);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
        return null;
    }


}
