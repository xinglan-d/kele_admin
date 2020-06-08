package com.kele.system.model.intercept;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kele.base.model.util.UserUtil;
import com.kele.system.dao.dto.UserDO;
import com.kele.system.model.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 自定义安全拦截器
 *
 * @author duzongyue
 * @date 2020/06/05
 */
@Slf4j
@Component
public class CustomSecurityInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String businessUser = request.getParameter("businessUser");
        if (businessUser != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDO userDO = objectMapper.readValue(businessUser, UserDO.class);
            User user = new User(userDO);
            UserUtil.setUserInfo(user);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception arg3)
            throws Exception {
        // 整个请求处理完毕回调方法，即在视图渲染完毕时回调
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
        // 后处理回调方法，实现处理器的后处理（但在渲染视图之前）
    }
}
