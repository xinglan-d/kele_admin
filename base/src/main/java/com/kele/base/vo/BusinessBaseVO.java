package com.kele.base.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.dao.jpa.PageParameter;
import com.kele.base.model.annotation.base.BusinessColumn;
import com.kele.base.model.annotation.page.TableColumn;
import com.kele.base.model.util.BusinessUtils;
import com.kele.base.util.BeanUtil;
import com.kele.base.vo.page.SearchVO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:业务数据层
 * @author: dzy
 * @createDate: 2020/1/20 14:30
 * @version: 1.0
 */
@Getter
@Setter
@Log4j2
public class BusinessBaseVO<D extends BusinessBaseDO> {

    @BusinessColumn
    @TableColumn(isCheckbox = true)
    protected String primaryKey;

    //页码
    private Integer pageNumber;

    //每页查询数量
    private Integer pageSize;

    //搜索栏
    private List<SearchVO> search = new ArrayList<>();

    public void setDo(D doData) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        setDo(doData, (Class[]) null);
    }

    public void setDo(D doData, Class annotationClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        setDo(doData, new Class[]{annotationClass});
    }

    public void setDo(D doData, Class[] annotations) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        //获取vo的数据集
        BeanUtil beanUtil = new BeanUtil(this);
        //获取所有变量
        List<Field> fields = getVOFields();
        //获取do的数据集
        BeanUtil doBeanUtil = new BeanUtil(doData);
        //遍历vo变量
        for (Field field : fields) {
            //判断是否扫描带有特定注解的类
            if (annotations != null) {
                boolean isContinue = true;//是否跳过当前变量
                for (Class annotation : annotations) {
                    Annotation annotationFather = field.getAnnotation(annotation);
                    if (annotationFather != null) {
                        isContinue = false;
                        break;
                    }
                }
                if (isContinue) {
                    continue;
                }
            }
            //获取对应的变量名注解 有注解的用注解优先    无注解的使用变量名
            String[] fieldName = getFieldName(field);
            //获取对应的变量值
            Object value = getValue(doBeanUtil, fieldName);
            //将获取到的值放入vo对象中
            if (value != null) {
                //TODO 需要在这里添加判断是否获取的是array数据
                if (field.getType().isAssignableFrom(List.class)) {
                    List list = (List) value;
                    Class<?>[] classes = getParameterizedType(field);
                    for (Class<?> aClass : classes) {
                        BusinessUtils businessUtils = new BusinessUtils(aClass);
                        value = businessUtils.dosToVos(list, annotations);
                    }
                    beanUtil.setValues(field.getName(), value);
                } else {
                    beanUtil.setValue(field.getName(), value);
                }

            }
        }
    }

    /**
     * 获取当前vo和父vo的变量
     *
     * @param
     * @return java.util.List<java.lang.reflect.Field>
     * @author duzongyue
     * @date 2020-04-05 10:52:22
     */
    @JsonIgnore
    public List<Field> getVOFields() {
        return getVOFields(this.getClass());
    }

    @JsonIgnore
    public List<Field> getVOFields(Class clazz) {
        List<Field> list = new ArrayList<>();
        if (clazz == null) {
            return list;
        }
        list.addAll(getVOFields(clazz.getSuperclass()));
        list.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
        return list;
    }

    /**
     * 获取do对应的数据集
     *
     * @param doBeanUtil
     * @param fieldName
     * @return java.lang.Object
     * @author duzongyue
     * @date 2020-04-05 10:54:51
     */
    private Object getValue(BeanUtil doBeanUtil, String... fieldName) throws InvocationTargetException, IllegalAccessException {
        return getValue(doBeanUtil, fieldName, 0);
    }

    private Object getValue(BeanUtil beanUtil, String[] fieldName, int i) throws InvocationTargetException, IllegalAccessException {
        if (i == fieldName.length - 1) {
            return beanUtil.getValue(fieldName[i]);
        }
        Object value = beanUtil.getValue(fieldName[i]);
        return getValue(new BeanUtil(value), fieldName, ++i);
    }

    /**
     * 获取字段的名字用于获取对应的值
     *
     * @param field
     * @return java.lang.String[]
     * @author duzongyue
     * @date 2020-03-28 02:02:32
     */
    private String[] getFieldName(Field field) {
        String fieldName;
        BusinessColumn businessColumn = field.getAnnotation(BusinessColumn.class);
        if (businessColumn != null && StringUtils.isNotBlank(businessColumn.columnName())) {
            fieldName = businessColumn.columnName();
        } else {
            fieldName = field.getName();
        }
        return fieldName.split("[.]");
    }


    /**
     * 获取某一个字段上面的泛型参数数组,典型的就是获取List对象里面是啥参数
     *
     * @param f
     * @return
     */
    public static Class<?>[] getParameterizedType(Field f) {
        // 获取f字段的通用类型
        Type fc = f.getGenericType(); // 关键的地方得到其Generic的类型
        // 如果不为空并且是泛型参数的类型
        if (fc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) fc;
            Type[] types = pt.getActualTypeArguments();
            if (types != null && types.length > 0) {
                Class<?>[] classes = new Class<?>[types.length];
                for (int i = 0; i < classes.length; i++) {
                    classes[i] = (Class<?>) types[i];
                }
                return classes;
            }
        }
        return new Class[0];
    }

    /**
     * 获取搜索参数
     *
     * @param
     * @return com.kele.base.dao.jpa.PageParameter
     * @author duzongyue
     * @date 2020-04-15 07:41:43
     */
    @JsonIgnore
    public PageParameter getPageParameter() {
        PageParameter pageParameter = new PageParameter(pageNumber, pageSize);
        pageParameter.setSearch(getSearchVOS(this.search));
        return pageParameter;
    }

    /**
     * 封装搜索的vo参数
     *
     * @param searchVOList
     * @return java.util.List<com.kele.base.vo.page.SearchVO>
     * @author duzongyue
     * @date 2020-04-15 07:42:05
     */
    @JsonIgnore
    private List<SearchVO> getSearchVOS(List<SearchVO> searchVOList) {
        if (searchVOList == null) {
            searchVOList = new ArrayList<>();
        }
        //获取vo的数据集
        BeanUtil beanUtil = new BeanUtil(this);
        for (SearchVO search : searchVOList) {
            Object value;
            search.setAClass(beanUtil.getFiledClass(search.getName()));
            try {
                value = beanUtil.getValue(search.getName());
            } catch (InvocationTargetException | IllegalAccessException e) {
                value = null;
                log.error(e.getMessage(), e);
            }
            if (search.getValue() == null && value != null) {
                search.setValue(value);
            }
        }
        return searchVOList;
    }

    @JsonIgnore
    public D getDO() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class dClass = (Class) parameterizedType.getActualTypeArguments()[0];
        if (dClass != null) {
            //获取vo的数据集
            BeanUtil beanUtil = new BeanUtil(this);
            //创建实例对象
            D data = (D) dClass.newInstance();
            BeanUtil doBeanUtil = new BeanUtil(data);
            List<Field> voFields = getVOFields();
            for (Field field : voFields) {
                BusinessColumn businessColumn = field.getAnnotation(BusinessColumn.class);
                if (businessColumn != null) {
                    //通过变量名获取数据
                    String[] fieldName = getFieldName(field);
                    Object value = getValue(beanUtil, fieldName);
                    //如果是主键的话遍历do对象放入对应的id参数
                    if (value != null) {
                        //TODO 这个在新增的情况下永远不会走
                        if ("primaryKey".equals(field.getName())) {
                            String doIdName = getDoIdName(data, value);
                            if (doIdName != null) {
                                doBeanUtil.setValue(doIdName, value);
                            }
                        }
                        setValue(doBeanUtil, fieldName, value);
                    }
                }
            }
            return data;
        }
        return null;
    }

    //保存数据
    private void setValue(BeanUtil doBeanUtil, String[] fieldName, Object value) throws InvocationTargetException, IllegalAccessException {
        doBeanUtil.setValue(fieldName[0], value);
    }


    //获取do对象的id的变量名
    @JsonIgnore
    private String getDoIdName(D data, Object value) {
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field field : fields) {
            Id annotation = field.getAnnotation(Id.class);
            if (annotation != null) {
                return field.getName();
            }
        }
        return null;
    }
}
