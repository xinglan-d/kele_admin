package com.kele.base.service.base.impl;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.dao.jpa.BusinessBaseDao;
import com.kele.base.dao.jpa.PageParameter;
import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.edit.FormColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.model.util.BusinessUtils;
import com.kele.base.model.util.SpringUtil;
import com.kele.base.service.base.BusinessService;
import com.kele.base.util.BeanUtil;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.base.vo.page.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

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
@Log4j2
public class BusinessServiceImpl<V extends BusinessBaseVO, D extends BusinessBaseDO> implements BusinessService<V, D> {

    //视图层对应的bean
    private Class<V> voClass;
    //数据库对应的bean
    private Class<D> doClass;

    //dao层
    private BusinessBaseDao baseDao = null;

    //视图层对应的变量 使用地方较多一次缓存
    private List<Field> voFields;

    public BusinessServiceImpl(Class<V> voClass, Class<D> doClass) {
        this.doClass = doClass;
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
        voFields.forEach(voField -> {
            //获取是否当前字段设置列表注解
            TableColumn tableColumn = voField.getAnnotation(TableColumn.class);
            if (tableColumn != null) {
                //获取业务注解
                BusinessColumn businessColumn = voField.getAnnotation(BusinessColumn.class);
                TableColumnParam tableColumnParam = new TableColumnParam();
                tableColumnParam.setField(voField.getName());
                if (businessColumn != null) {
                    tableColumnParam.setColumnName(businessColumn.columnName());
                    tableColumnParam.setTitle(businessColumn.value());
                }
                tableColumnParam.setSearch(tableColumn.search());
                tableColumnParam.setCheckbox(tableColumn.isCheckbox());
                tableColumnParam.setType(businessColumn.type().getType());
                if (StringUtils.isNotBlank(businessColumn.url())) {
                    tableColumnParam.setUrl(businessColumn.url());
                }
                columns.add(tableColumnParam);
            }
        });

        PageAttrVO pageAttrVO = new PageAttrVO();
        pageAttrVO.setColumns(columns);
        return pageAttrVO;
    }

    @Override
    public EditAttrVO getEditAttr(String primaryKey) throws InvocationTargetException, IllegalAccessException {

        BeanUtil doBeanUtil = null;
        if (StringUtils.isNotBlank(primaryKey)) {
            D doData = (D) getBaseDao().findById(primaryKey);
            doBeanUtil = new BeanUtil(doData);
        }
        List<FormColumnParam> columns = new ArrayList<>();
        for (Field voField : voFields) {
            //获取需要form表格渲染的菜单
            FormColumn formColumn = voField.getAnnotation(FormColumn.class);
            //form表单不许不存在businessColumn注解
            BusinessColumn businessColumn = voField.getAnnotation(BusinessColumn.class);
            if (formColumn != null && businessColumn != null) {
                FormColumnParam formColumnParam = new FormColumnParam();
                formColumnParam.setField(voField.getName());
                formColumnParam.setTitle(businessColumn.value());
                formColumnParam.setRules(getRules(voField, formColumn));
                formColumnParam.setHide(formColumn.hide());
                formColumnParam.setType(businessColumn.type().getType());
                if (doBeanUtil != null) {
                    String[] columnName;
                    if (StringUtils.isNotBlank(businessColumn.columnName())) {
                        columnName = businessColumn.columnName().split("[.]");
                    } else {
                        columnName = new String[]{voField.getName()};
                    }
                    formColumnParam.setValue(getValue(doBeanUtil, columnName));
                }
                columns.add(formColumnParam);
            }
        }
        EditAttrVO editAttrVO = new EditAttrVO();
        editAttrVO.setColumns(columns);
        return editAttrVO;
    }

    private Object getValue(BeanUtil doBeanUtil, String[] columnName) {
        return getValue(doBeanUtil, columnName, 0);
    }

    /**
     * 获得值
     *
     * @param doBeanUtil bean工具类
     * @param columnName 列名
     * @param i          循环值
     * @return {@link Object}
     */
    private Object getValue(BeanUtil doBeanUtil, String[] columnName, int i) {
        Object value = null;
        try {
            value = doBeanUtil.getValue(columnName[i]);
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        if (columnName.length - 1 == i) {
            return value;
        }
        return getValue(new BeanUtil(value), columnName, ++i);
    }

    @Override
    public void addVO(V vo) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        D aDo = (D) vo.getDO();
        getBaseDao().save(aDo);
    }

    @Override
    public void editVO(V vo) throws InvocationTargetException, IllegalAccessException {
        //获取当前vo对应的id
        String primaryKey = vo.getPrimaryKey();
        D dataDo = (D) getBaseDao().findById(primaryKey);
        D aDo = (D) vo.getDO(dataDo);
        getBaseDao().merge(aDo);
    }

    @Override
    public void del(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        for (String id : idList) {
            D doData = (D) getBaseDao().findById(id);
            getBaseDao().remove(doData);
        }
    }

    /**
     * 获取校验规则
     *
     * @param voField
     * @param formColumn
     * @return java.lang.String
     * @author duzongyue
     * @date 2020-04-22 10:02:10
     */
    private RulesVO getRules(Field voField, FormColumn formColumn) {
        RulesVO rulesVO = new RulesVO();
        //获取输入类型
        if (StringUtils.isNotBlank(formColumn.type())) {
            rulesVO.setType(formColumn.type());
        } else if (String.class.isAssignableFrom(voField.getType())) {
            rulesVO.setType("string");
        } else if (Integer.class.isAssignableFrom(voField.getType())) {
            rulesVO.setType("number");
        }
        //是否必须输入
        rulesVO.setRequired(formColumn.required());
        //最大输入数量 -1不限制
        if (formColumn.max() != -1) {
            rulesVO.setMax(formColumn.max());
        }
        //最少输入数量 -1不限制
        if (formColumn.min() != -1) {
            rulesVO.setMin(formColumn.min());
        }
        return rulesVO;
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
