package com.kele.base.controller;

import com.kele.base.dao.data.BusinessBaseDO;

public interface BusinessInterface<D extends BusinessBaseDO> {

    void addBefore(D doData);
}
