package com.kele.base.service.base.impl;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.dao.jpa.BusinessBaseDao;
import com.kele.base.dao.jpa.PageParameter;
import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.model.util.BusinessUtils;
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


    private Class<V> voClass;
    private Class<D> doClass;

    BusinessBaseDao baseDao;

    List<Field> voFields;

    @Override
    public PageData<V> getAll(V vo) {
        //获取查询参数
        PageParameter pageParameter;
        if (vo != null) {
            pageParameter = vo.getPageParameter();
        } else {
            pageParameter = new PageParameter(1, 10);
        }
        //封装查询参数
        Integer total = getBaseDao().getTotal(pageParameter);//获取总数量
        List data = getBaseDao().findAll(pageParameter);//获取数据
        BusinessUtils<V, D> businessUtils = new BusinessUtils(voClass);
        PageData<V> pageData = new PageData<>();
        try {
            List<V> row = businessUtils.dosToVos(data, TableColumn.class);//获取列表页面数据
            pageData.setRows(row);
            pageData.setTotal(total);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            log.error(e.getMessage(), e);
        }

        return pageData;
    }

    @Override
    public void setVOClass(Class<V> voClass) {
        this.voClass = voClass;
        try {
            voFields = voClass.getDeclaredConstructor().newInstance().getVOFields();
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
            baseDao = SpringUtil.getBean(BusinessBaseDao.class);
            baseDao.setIdClass(String.class);
            baseDao.setDoClass(doClass);
        }
        return baseDao;
    }
}
