package com.kele.base.model.sys;

public interface UserInfo<T> {

    boolean isAdmin();

    T getUser();

    void setUser(T user);

    <D> D getDept();

    String getDeptSeq();

    String getUserId();
}
