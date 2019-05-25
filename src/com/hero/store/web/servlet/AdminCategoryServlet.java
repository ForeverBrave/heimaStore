package com.hero.store.web.servlet;

import com.hero.store.domain.Category;
import com.hero.store.service.CategoryService;
import com.hero.store.service.serviceimpl.CategoryServiceImpl;
import com.hero.store.utils.UUIDUtils;
import com.hero.store.web.base.BaseServlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminCategoryServlet",value = "/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {

    //findAllCats
    public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取全部分类信息
        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> list = categoryService.getAllCats();
        //全部分类信息放入request中
        req.setAttribute("allCats",list);
        //转发到 /admin/category/list.jsp
        return "/admin/category/list.jsp";
    }

    //addCategoryUI
    public String addCategoryUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        return "/admin/category/add.jsp";
    }

    //addCategory
    public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        //获取分类名称
        String cname = req.getParameter("cname");
        //创建分类ID
        String id = UUIDUtils.getId();
        Category category = new Category();
        category.setCid(id);
        category.setCname(cname);
        //调用业务层添加分类功能
        CategoryService categoryService = new CategoryServiceImpl();
        categoryService.addCategory(category);
        //重定向到查询全部分类信息
        resp.sendRedirect("/store_v5/AdminCategoryServlet?method=findAllCats");
        return null;
    }
}
