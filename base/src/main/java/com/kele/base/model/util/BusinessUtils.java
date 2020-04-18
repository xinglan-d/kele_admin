package com.kele.base.model.util;

import com.kele.base.dao.data.BusinessBaseDO;
import com.kele.base.vo.BusinessBaseVO;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:业务工具类
 * @author: duzongyue
 * @createDate: 2020/03/28 21:54
 * @version: 1.0
 */
public class BusinessUtils<V extends BusinessBaseVO, D extends BusinessBaseDO> {

    private Class<V> voClass;

    public BusinessUtils(Class<V> voClass) {
        this.voClass = voClass;
    }

    public List<V> dosToVos(List<D> doDataList) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return dosToVos(doDataList, (Class[]) null);
    }

    public List<V> dosToVos(List<D> doDataList, Class annotationClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return dosToVos(doDataList, new Class[]{annotationClass});
    }

    public List<V> dosToVos(List<D> doDataList, Class[] classes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<V> vos = new ArrayList<>();
        for (D doData : doDataList) {
            V vo = voClass.getDeclaredConstructor().newInstance();
            vo.setDo(doData, classes);
            vos.add(vo);
        }
        return vos;
    }
}
