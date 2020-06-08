package com.kele.system.model.vo;

import com.kele.base.model.sys.UserInfo;
import com.kele.system.dao.dto.UserDO;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/06/05 10:39
 * @version: 1.0
 */
public class User implements UserInfo<UserDO> {

    private UserDO user;

    public User(UserDO user) {
        this.user = user;
    }

    @Override
    public boolean isAdmin() {
        if (user.getIsAdmin() == null) {
            return false;
        }
        return user.getIsAdmin();
    }

    @Override
    public UserDO getUser() {
        return user;
    }

    @Override
    public void setUser(UserDO user) {
        this.user = user;
    }

    @Override
    public <D> D getDept() {
        return (D) user.getDept();
    }

    @Override
    public String getDeptSeq() {
        return user.getDept().getSeq();
    }

    @Override
    public String getUserId() {
        if (user != null) {
            return user.getUserId();
        }
        return null;
    }
}
