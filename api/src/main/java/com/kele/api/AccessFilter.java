package com.kele.api;

import com.kele.api.util.JwtUtil;
import com.kele.api.util.RedisCacheUtil;
import com.kele.api.util.SpringUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源过滤器
 * 所有的资源请求在路由之前进行前置过滤
 * 如果请求头不包含 Authorization参数值，直接拦截不再路由
 */
@Component
public class AccessFilter extends ZuulFilter {

    private static final String ADMIN_USER_PREFIX = "admin_";
    private static RedisCacheUtil redisCacheUtil;

    private static final JwtUtil jwtUtil = new JwtUtil();

    public static RedisCacheUtil getRedisCacheUtil() {
        if (redisCacheUtil == null) {
            redisCacheUtil = SpringUtil.getBean(RedisCacheUtil.class);
        }
        return redisCacheUtil;
    }

    /**
     * 过滤器的类型 pre表示请求在路由之前被过滤
     *
     * @return 类型
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序
     *
     * @return 顺序 数字越大表示优先级越低，越后执行
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 过滤器是否会被执行
     *
     * @return true
     */
    @Override
    public boolean shouldFilter() {
        //TODO 在这里判断是否需要走过滤器 url的黑白名单在这里进行校验
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String servletPath = request.getServletPath();
        List<String> blockLists = getRedisCacheUtil().getCacheList("blockList");
        if (blockLists != null && blockLists.size() != 0) {
            if (blockLists.contains(servletPath)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 过滤逻辑
     *
     * @return 过滤结果
     */
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        System.out.println("尝试访问:" + request.getServletPath());
        String token = getToken(request);
        //在这里对用户进行鉴权
        if (token != null) {
            String id = jwtUtil.verifyToken(token);
            String userJson = getRedisCacheUtil().getCacheObject(ADMIN_USER_PREFIX + id);
            if (userJson != null) {
                requestContext.setSendZuulResponse(true);
                //3、将转换后的数据放入请求参数中
                request.getParameterMap();
                Map<String, List<String>> requestQueryParams = requestContext.getRequestQueryParams();
                if (requestQueryParams == null)
                    requestQueryParams = new HashMap<>();
                //4、将要新增的参数添加进去,被调用的微服务可以直接 去取,就想普通的一样,框架会直接注入进去
                ArrayList<String> paramsList = new ArrayList<>();
                paramsList.add(userJson);
                requestQueryParams.put("businessUser", paramsList);
                requestContext.setRequestQueryParams(requestQueryParams);
                return null;
            }
        }
        //当前url不会被路由
        requestContext.setSendZuulResponse(false);
        //返回鉴权失败的401
        requestContext.setResponseStatusCode(401);
        return null;
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            token = token.replaceAll("Bearer", "");
            return token.trim();
        }
        return null;
    }
}
