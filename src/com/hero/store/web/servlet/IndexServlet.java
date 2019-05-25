package com.hero.store.web.servlet;

import com.hero.store.domain.Category;
import com.hero.store.domain.Product;
import com.hero.store.service.CategoryService;
import com.hero.store.service.ProductService;
import com.hero.store.service.serviceimpl.CategoryServiceImpl;
import com.hero.store.service.serviceimpl.ProductServiceImpl;
import com.hero.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet",value = "/IndexServlet")
public class IndexServlet extends BaseServlet {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

//        CategoryService categoryService = new CategoryServiceImpl();
//        List<Category> list = categoryService.getAllCats();
//
//        req.setAttribute("allCats",list);

        //调用业务层查询最新商品，最热商品，返回两个list集合
        ProductService productService = new ProductServiceImpl();
        List<Product> list01 = productService.findHots();
        List<Product> list02 = productService.findNews();
        //将两个集合放入request中
        req.setAttribute("hots",list01);
        req.setAttribute("news",list02);

        //转发到真实首页
        return "/jsp/index.jsp";
    }
}
