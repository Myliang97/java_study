package com.liang.filter;

import com.liang.common.exception.CustomException;
import com.liang.common.helper.JwtHelper;
import com.liang.common.result.ResponseUtil;
import com.liang.common.result.Result;
import com.liang.common.result.ResultCodeEnum;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    public TokenAuthenticationFilter() {

    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //如果是登录接口，直接放行
        String uri = request.getRequestURI();
        if("/admin/system/index/login".equals(uri)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if(null != authentication) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.TOKEN_EXPIRED));
        }
    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token)) {
            try
            {
                String username = JwtHelper.getUsername(token);
                //这里看下要不要加数据库或者cache查询？
                if (!StringUtils.isEmpty(username)) {
                    return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                }
            }
            catch (JwtException e){
            }
        }
        return null;
    }
}
