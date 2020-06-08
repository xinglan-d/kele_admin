package com.kele.base.model.aspect;

import com.kele.base.controller.BusinessInterface;
import com.kele.base.controller.Result;
import com.kele.base.model.util.SpringUtil;
import com.kele.base.service.ResultService;
import com.kele.base.service.base.BusinessService;
import com.kele.base.service.base.impl.BusinessServiceImpl;
import com.kele.base.util.BusinessUtil;
import com.kele.base.vo.BusinessBaseVO;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/06/07 18:56
 * @version: 1.0
 */
@Aspect
@Component
@Log4j2
public class BusinessAspect {


    /**
     * 获取页面的属性
     *
     * @param point 点
     * @return {@link Result<?>}
     */
    @Around("@annotation(com.kele.base.model.annotation.base.PageAttr) && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Result<?> pageAttr(ProceedingJoinPoint point) {
        /*result为连接点的放回结果*/
        Object result = null;
        try {
            Class<?> aClass = point.getTarget().getClass();
            BusinessService businessService = getBusinessService(point);
            String url = getClassUrl(aClass);
            result = businessService.getPageAttr(url);
        } catch (Throwable e) {
            return ResultService.error(e.getMessage());
        }
        return ResultService.success(result);
    }

    /**
     * 获取数据
     *
     * @param point 点
     * @return {@link Result<?>}
     */
    @Around("@annotation(com.kele.base.model.annotation.base.GetAspect) && @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Result<?> getAspect(ProceedingJoinPoint point) {
        /*result为连接点的放回结果*/
        Object result = null;
        try {
            BusinessService businessService = getBusinessService(point);
            Object[] args = point.getArgs();
            //判断当前参数那个集成啦BusinessBaseVO
            BusinessBaseVO businessBaseVO =
                    Arrays.stream(args).
                            filter(arg -> BusinessBaseVO.class.isAssignableFrom(arg.getClass())).
                            map(arg -> (BusinessBaseVO) arg).
                            findAny().
                            orElse(null);
            if (businessBaseVO != null) {
                result = businessService.getAll(businessBaseVO);
            }
        } catch (Throwable e) {
            return ResultService.error(e.getMessage());
        }

        return ResultService.success(result);
    }

    @Around("@annotation(com.kele.base.model.annotation.base.EditAttr) && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Result<?> EditAttr(ProceedingJoinPoint point) {
        /*result为连接点的放回结果*/
        Object result = null;
        try {
            BusinessService businessService = getBusinessService(point);
            Object[] args = point.getArgs();
            String primaryKey = Arrays.stream(args).
                    map(String::valueOf).
                    findAny().
                    orElse(null);

            result = businessService.getEditAttr(primaryKey);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            return ResultService.error(e.getMessage());
        }
        return ResultService.success(result);
    }

    @Around("@annotation(com.kele.base.model.annotation.base.EditAspect) && @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Result<?> Edit(ProceedingJoinPoint point) {
        /*result为连接点的放回结果*/
        Object result = null;
        try {
            BusinessService businessService = getBusinessService(point);
            Object[] args = point.getArgs();
            //判断当前参数那个集成啦BusinessBaseVO
            BusinessBaseVO businessBaseVO =
                    Arrays.stream(args).
                            filter(arg -> BusinessBaseVO.class.isAssignableFrom(arg.getClass())).
                            map(arg -> (BusinessBaseVO) arg).
                            findAny().
                            orElse(null);
            if (businessBaseVO != null) {
                if (StringUtils.isBlank(businessBaseVO.getPrimaryKey())) {
                    businessService.addVO(businessBaseVO);
                } else {
                    businessService.editVO(businessBaseVO);
                }
            }
        } catch (Throwable e) {
            return ResultService.error(e.getMessage());
        }
        return ResultService.success(result);
    }


    @Around("@annotation(com.kele.base.model.annotation.base.DelAspect) && @annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Result<?> delete(ProceedingJoinPoint point) {
        try {
            BusinessService businessService = getBusinessService(point);
            Object[] args = point.getArgs();
            String ids = Arrays.stream(args).
                    map(String::valueOf).
                    findAny().
                    orElse(null);
            businessService.del(ids);
        } catch (Throwable e) {
            return ResultService.error(e.getMessage());
        }
        return ResultService.success(null);
    }

    @Around("@annotation(com.kele.base.model.annotation.base.SelectAspect) && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Result<?> select(ProceedingJoinPoint point) {
        /*result为连接点的放回结果*/
        Object result = null;
        try {
            BusinessService businessService = getBusinessService(point);
            Object[] args = point.getArgs();
            String field = Arrays.stream(args).
                    map(String::valueOf).
                    findAny().
                    orElse(null);
            result = businessService.getSelects(field);
        } catch (Throwable e) {
            return ResultService.error(e.getMessage());
        }
        return ResultService.success(result);
    }


    public BusinessService getBusinessService(ProceedingJoinPoint point) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获取当前类的对象
        Class<?> aClass = point.getTarget().getClass();
        //获取vo对象
        Class<?> aClass1 = BusinessUtil.getTClassName(aClass, 0);
        //获取do对象
        Class<?> aClass2 = BusinessUtil.getTClassName(aClass, 1);
        //获取当前服务类
        BusinessService businessService = new BusinessServiceImpl(aClass1, aClass2);
        //判断是否实现啦接口类
        if (BusinessInterface.class.isAssignableFrom(aClass)) {
            //如果实现啦接口代表开发人员需要自己控制某些状态
            BusinessInterface businessInterface = (BusinessInterface) SpringUtil.getBean(aClass);
            businessService.setBusinessInterface(businessInterface);
        }
        return businessService;
    }


    public String getClassUrl(Class<?> aClass) {
        RequestMapping annotation = aClass.getAnnotation(RequestMapping.class);
        if (annotation != null) {
            if (annotation.value() != null && annotation.value().length != 0) {
                return annotation.value()[0];
            }
        }
        return null;
    }
}

