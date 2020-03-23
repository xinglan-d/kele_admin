package com.kele.base.service.base.impl;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.dao.jpa.BusinessBaseDao;
import com.kele.base.dao.jpa.PageParameter;
import com.kele.base.dao.jpa.impl.BusinessBaseDaoImpl;
import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.service.base.BusinessService;
import com.kele.base.model.util.SpringUtil;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.base.vo.page.PageData;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: dzy
 * @createDate: 2020/1/20 14:52
 * @version: 1.0
 */
@Service
public class BusinessServiceImpl<V extends BusinessBaseVO, D extends BusinessBaseDO> implements BusinessService<V, D> {


    private Class<V> voClass;
    private Class<D> doClass;

    BusinessBaseDao baseDao;

    Field[] voFields;

    @Override
    public void getAll(int page, int size) {
        PageParameter pageParameter = new PageParameter(page, size);
        List<D> data = getBaseDao().findAll(pageParameter);
        PageData<V> pageData = doToVO(data);
    }


    private PageData<V> doToVO(List<D> data) {
        List<V> vList = new ArrayList<>();
        for (D d : data) {
            doToVO(d);
        }

        return null;
    }

    private PageData<V> doToVO(D data) {
        List<V> vList = new ArrayList<>();
        for (Field voField : voFields) {
            BusinessColumn businessColumn = voField.getAnnotation(BusinessColumn.class);
            System.out.println(businessColumn);
        }

        return null;
    }

    @Override
    public void setVOClass(Class<V> voClass) {
        this.voClass = voClass;
        voFields = voClass.getDeclaredFields();

    }

    @Override
    public void setDOClass(Class<D> doClass) {
        this.doClass = doClass;
    }


    public BusinessBaseDao getBaseDao() {
        if (baseDao == null) {
            baseDao = SpringUtil.getBean(BusinessBaseDaoImpl.class);
            baseDao.setIdClass(String.class);
            baseDao.setDoClass(doClass);
        }
        return baseDao;
    }
}
