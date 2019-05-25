package com.hero.store.web.servlet;

import com.hero.store.domain.User;
import com.hero.store.service.UserService;
import com.hero.store.service.serviceimpl.UserServiceImpl;
import com.hero.store.utils.MailUtils;
import com.hero.store.utils.MyBeanUtils;
import com.hero.store.utils.UUIDUtils;
import com.hero.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import com.hero.store.web.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

@WebServlet(name = "UserServlet",value = "/UserServlet")
public class UserServlet extends BaseServlet {
    public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/jsp/register.jsp";
    }

    public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/jsp/login.jsp";
    }

    //userRegist
    public String userRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //接收表单参数
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();

        MyBeanUtils.populate(user,map);
        //为用户的其他属性赋值
        user.setUid(UUIDUtils.getId());
        user.setState(0);
        user.setCode(UUIDUtils.getCode());
        System.out.println(user);

//        1_创建时间类型的转换器
//        DateConverter dt = new DateConverter();
//        2_设置转换的格式
//        dt.setPattern("yyyy-MM-dd");
//        3_注册转换器
//        ConvertUtils.register(dt, java.util.Date.class);
//        BeanUtils.populate(user,map);

        //调用业务层注册功能
        UserService userService = new UserServiceImpl();
        try {
            userService.userRegist(user);
            //注册成功，向用户邮箱发送信息，跳转到提示页面
            //发送邮箱
            MailUtils.sendMail(user.getEmail(),user.getCode());
            request.setAttribute("msg","用户注册成功，请激活！");

        } catch (Exception e) {
            //注册失败，跳转到提示页面
            request.setAttribute("msg","用户注册失败，请重新注册！");

        }
        return "/jsp/info.jsp";
        //注册成功，向用户发送信息，跳转到提示页面

        //注册失败，跳转到提示页面
    }
    public String active(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String code = request.getParameter("code");
        UserService userService = new UserServiceImpl();
        boolean flag = userService.userActive(code);
        if(flag == true){
            //用户激活成功
            request.setAttribute("msg","用户激活成功，请登录！");
            return "/jsp/login.jsp";
        }else{
            //用户激活失败
            request.setAttribute("msg","用户激活失败，请重新激活！");
            return "/jsp/info.jsp";
        }

    }

    public String userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

        User user = new User();
        MyBeanUtils.populate(user,request.getParameterMap());

        UserService userService = new UserServiceImpl();
        User user02 = null;

        try {
            //用户登录成功
            user02 = userService.userLogin(user);
            //用户登录成功，将用户信息放入sessio中
            request.getSession().setAttribute("loginUser",user02);
            response.sendRedirect("/store_v5/index.jsp");
            return null;

        } catch (Exception e) {
            //用户登录失败
            String msg = e.getMessage();
            System.out.println(msg);
            //向request中放入失败信息
            request.setAttribute("msg",msg);
            return "/jsp/login.jsp";
        }
     }

    public String logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //清除session
            request.getSession().invalidate();
        //重新定向到首页
            response.sendRedirect("/store_v5/index.jsp");
            return null;
    }
    }
