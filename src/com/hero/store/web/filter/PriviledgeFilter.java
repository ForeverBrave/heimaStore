package com.hero.store.web.filter;

import com.hero.store.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "PriviledgeFilter")
public class PriviledgeFilter implements Filter {

    public PriviledgeFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest myReq = (HttpServletRequest)req;
        //判断当前的seesion中是否存在已经登录成功的用户
        User user = (User) myReq.getSession().getAttribute("loginUser");
        if(null != user){
            //如果存在，放行
            chain.doFilter(req, resp);
        }else {
            //如果不存在，转入到提示页面
            myReq.setAttribute("msg","请用户登录之后再去访问");
            //转入到提示页面
            myReq.getRequestDispatcher("/jsp/info.jsp").forward(req,resp);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
