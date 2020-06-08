package com.kele.base.model.util;

import com.kele.base.model.sys.UserInfo;

/**
 * @description:用户工具类
 * @author: duzongyue
 * @createDate: 2020/06/05 09:54
 * @version: 1.0
 */
public class UserUtil {

    private static final ThreadLocal<UserInfo> USER_INFO = new ThreadLocal<>();

    public static void setUserInfo(UserInfo user) {
        USER_INFO.set(user);
    }

    public static UserInfo getUserInfo() {
        return USER_INFO.get();
    }
}
