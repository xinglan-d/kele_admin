package com.kele.base.service.base.impl;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.dao.jpa.BusinessBaseDao;
import com.kele.base.dao.jpa.PageParameter;
import com.kele.base.dao.jpa.impl.BusinessBaseDaoImpl;
import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.model.util.SpringUtil;
import com.kele.base.service.base.BusinessService;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.base.vo.page.PageAttrVO;
import com.kele.base.vo.page.PageData;
import com.kele.base.vo.page.TableColumnParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: dzy
 * @createDate: 2020/1/20 14:52
 * @version: 1.0
 */
@Service
@Log4j2
public class BusinessServiceImpl<V extends BusinessBaseVO, D extends BusinessBaseDO> implements BusinessService<V, D> {

    public BusinessServiceImpl() {
    }

    public BusinessServiceImpl(Class<V> voClass, Class<D> doClass) {
        this.voClass = voClass;
        this.doClass = doClass;
    }

    private Class<V> voClass;
    private Class<D> doClass;

    BusinessBaseDao baseDao;

    List<Field> voFields;

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
        try {
            voFields = voClass.getDeclaredConstructor().newInstance().getFields();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
            voFields = Arrays.asList(voClass.getDeclaredFields());
        }

    }

    @Override
    public void setDOClass(Class<D> doClass) {
        this.doClass = doClass;
    }

    @Override
    public PageAttrVO getPageAttr() {
        List<TableColumnParam> columns = new ArrayList<>();
        for (Field voField : voFields) {
            TableColumn tableColumn = voField.getAnnotation(TableColumn.class);
            if (tableColumn != null) {
                BusinessColumn businessColumn = voField.getAnnotation(BusinessColumn.class);
                TableColumnParam tableColumnParam = new TableColumnParam();
                tableColumnParam.setField(voField.getName());
                if (businessColumn != null) {
                    tableColumnParam.setColumnName(businessColumn.columnName());
                    tableColumnParam.setTitle(businessColumn.value());
                }
                tableColumnParam.setSearch(tableColumn.search());
                tableColumnParam.setCheckbox(tableColumn.isCheckbox());
                columns.add(tableColumnParam);
            }
        }

        PageAttrVO pageAttrVO = new PageAttrVO();
        pageAttrVO.setColumns(columns);
        return pageAttrVO;
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
