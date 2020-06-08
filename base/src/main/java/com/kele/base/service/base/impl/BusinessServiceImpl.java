package com.kele.base.service.base.impl;

import com.kele.base.controller.BusinessInterface;
import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.dao.jpa.BusinessBaseDao;
import com.kele.base.dao.jpa.PageParameter;
import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.base.BusinessQuery;
import com.kele.base.model.annotation.base.DataFilter;
import com.kele.base.model.annotation.edit.FormColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.model.enumerate.base.ColumnType;
import com.kele.base.model.enumerate.base.QueryType;
import com.kele.base.model.enumerate.base.SearchEnum;
import com.kele.base.model.sys.UserInfo;
import com.kele.base.model.util.BusinessUtils;
import com.kele.base.model.util.EnumUtil;
import com.kele.base.model.util.SpringUtil;
import com.kele.base.model.util.UserUtil;
import com.kele.base.service.base.BusinessService;
import com.kele.base.util.BeanUtil;
import com.kele.base.util.BusinessUtil;
import com.kele.base.vo.BusinessBaseVO;
import com.kele.base.vo.Selects;
import com.kele.base.vo.page.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: dzy
 * @createDate: 2020/1/20 14:52
 * @version: 1.0
 */
@Log4j2
public class BusinessServiceImpl<V extends BusinessBaseVO<D>, D extends BusinessBaseDO> implements BusinessService<V, D> {

    //视图层对应的bean
    private Class<V> voClass;
    //数据库对应的bean
    private Class<D> doClass;
    //控制层回调方法
    private BusinessInterface<D> businessInterface;
    //dao层
    private BusinessBaseDao<D, String> baseDao = null;

    //视图层对应的变量 使用地方较多一次缓存
    private List<Field> voFields;

    public BusinessServiceImpl(Class<V> voClass, Class<D> doClass) {
        this.doClass = doClass;
        this.voClass = voClass;
        voFields = BusinessUtil.getFields(voClass);
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
        //初始化过滤参数 因为过滤参数本质上就是增加查询条件所以直接使用search 实现
        initFilter(pageParameter, vo.getClass());

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

    /**
     * 初始化过滤参数 因为过滤参数本质上就是增加查询条件所以直接使用search 实现
     *
     * @param pageParameter
     * @param aClass
     */
    private void initFilter(PageParameter pageParameter, Class<? extends BusinessBaseVO> aClass) {
        UserInfo userInfo = UserUtil.getUserInfo();
        if (userInfo.isAdmin()) {
            return;
        }
        DataFilter dataFilter = aClass.getAnnotation(DataFilter.class);
        if (dataFilter != null) {
            SearchVO searchVO = null;
            switch (dataFilter.mode()) {
                case DEPT:
                    String deptSeq = userInfo.getDeptSeq();
                    searchVO = new SearchVO(dataFilter.filterFiled(), deptSeq, SearchEnum.afterContain.getValue(), String.class);
                    break;
                case USER:
                    String userId = userInfo.getUserId();
                    searchVO = new SearchVO(dataFilter.filterFiled(), userId, SearchEnum.afterContain.getValue(), String.class);
                    break;
            }
            List<SearchVO> searchs = pageParameter.getSearch();
            if (searchs == null) {
                searchs = new ArrayList<>();
            }
            if (searchVO != null) {
                searchs.add(searchVO);
            }
        }
    }

    @Override
    public PageAttrVO getPageAttr(String url) {
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
                tableColumnParam.setHide(tableColumn.hide());
                tableColumnParam.setMultiple(businessColumn.multiple());//是否支持多选
                if (StringUtils.isNotBlank(businessColumn.url())) {
                    tableColumnParam.setUrl(businessColumn.url());
                } else if (businessColumn.type() == ColumnType.SELECT) {
                    //TODO 临时新一个回头要取消
                    tableColumnParam.setUrl("{service}" + url + "/select/" + voField.getName());
                }
                if (StringUtils.isNotBlank(tableColumn.filterField())) {
                    tableColumnParam.setFilterField(tableColumn.filterField());
                }
                columns.add(tableColumnParam);
            }
        });

        PageAttrVO pageAttrVO = new PageAttrVO();
        pageAttrVO.setColumns(columns);
        return pageAttrVO;
    }

    @Override
    public EditAttrVO getEditAttr(String primaryKey) throws Exception {
        D doData = null;
        if (StringUtils.isNotBlank(primaryKey)) {
            doData = (D) getBaseDao().findById(primaryKey);
        }
        List<FormColumnParam> columns = new ArrayList<>();
        for (Field voField : voFields) {
            //获取需要form表格渲染的菜单
            FormColumn formColumn = voField.getAnnotation(FormColumn.class);
            //form表单不许不存在businessColumn注解
            BusinessColumn businessColumn = voField.getAnnotation(BusinessColumn.class);
            if (formColumn != null && businessColumn != null) {
                FormColumnParam formColumnParam = new FormColumnParam();
                formColumnParam.setField(voField.getName());//字段名
                formColumnParam.setTitle(businessColumn.value());//列名
                formColumnParam.setRules(getRules(voField, formColumn));//校验规则
                formColumnParam.setHide(formColumn.hide());//是否隐藏
                formColumnParam.setType(businessColumn.type().getType());//输入类型
                formColumnParam.setMultiple(businessColumn.multiple());//是否支持多选
                if (StringUtils.isNotBlank(businessColumn.url())) {
                    formColumnParam.setUrl(businessColumn.url());//获取数据接口
                }
                if (StringUtils.isNotBlank(formColumn.pidField())) {
                    formColumnParam.setPidField(formColumn.pidField());//获取数据接口
                }
                if (doData != null) {
                    String[] columnName;
                    if (StringUtils.isNotBlank(businessColumn.columnName())) {
                        columnName = businessColumn.columnName().split("[.]");
                    } else {
                        columnName = new String[]{voField.getName()};
                    }
                    formColumnParam.setValue(doData.getFieldValue(Arrays.asList(columnName)));
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
    public void addVO(V vo) throws Exception {
        D aDo = vo.getDO();
        addFilter(vo, aDo);
        getBusinessInterface().addBefore(aDo);
        getBaseDao().save(aDo);
    }

    /**
     * 添加过滤值
     *
     * @param vo
     * @param aDo
     * @throws Exception 异常
     */
    private void addFilter(V vo, D aDo) throws Exception {
        DataFilter dataFilter = vo.getClass().getAnnotation(DataFilter.class);
        if (dataFilter != null) {
            String[] strings = dataFilter.filterFiled().split("\\.");
            Object value = null;
            switch (dataFilter.mode()) {
                case DEPT:
                    value = UserUtil.getUserInfo().getDeptSeq();
                    break;
                case USER:
                    value = UserUtil.getUserInfo().getUserId();
                    break;
            }
            if (value != null) {
                aDo.setFieldValue(Arrays.asList(strings), value);
            }
        }
    }

    @Override
    public void editVO(V vo) throws Exception {
        //获取当前vo对应的id
        String primaryKey = vo.getPrimaryKey();
        D dataDo = (D) getBaseDao().findById(primaryKey);
        D aDo = vo.getDO(dataDo);
        getBusinessInterface().addBefore(aDo);
        getBaseDao().merge(aDo);
    }

    @Override
    public void del(String ids) {
        if (StringUtils.isBlank(ids)) {
            return;
        }
        List<String> idList = Arrays.asList(ids.split(","));
        for (String id : idList) {
            D doData = (D) getBaseDao().findById(id);
            getBaseDao().remove(doData);
        }
    }


    @Override
    public Selects getSelects(String field) {
        for (Field voField : voFields) {
            if (voField.getName().equals(field)) {
                BusinessQuery businessQuery = voField.getAnnotation(BusinessQuery.class);
                if (businessQuery == null) {
                    return null;
                } else if (businessQuery.type() == QueryType.HQL) {
                    List<BusinessBaseDO> selects = getBaseDao().executeHql(businessQuery.value());
                    return new Selects(selects, businessQuery.idField(), businessQuery.nameField());
                } else if (businessQuery.type() == QueryType.SQL) {
                    List<Map<String, Object>> map = getBaseDao().executeSql(businessQuery.value());
                } else if (businessQuery.type() == QueryType.EMNU) {
                    return EnumUtil.enumTransferSelects(businessQuery.menu());
                }
            }
        }
        return null;
    }

    @Override
    public void setBusinessInterface(BusinessInterface businessInterface) {
        this.businessInterface = businessInterface;
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

    public BusinessBaseDao<D,String>getBaseDao() {
        if (baseDao == null) {
            baseDao = SpringUtil.getBean(BusinessBaseDao.class);
        }
        baseDao.setIdClass(String.class);
        baseDao.setDoClass(doClass);
        return baseDao;
    }

    public BusinessInterface<D> getBusinessInterface() {
        if (businessInterface == null) {
            businessInterface = new BusinessInterface<D>() {
                @Override
                public void addBefore(D doData) {

                }
            };
        }
        return businessInterface;
    }
}
