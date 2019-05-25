package com.hero.store.web.servlet;

import com.hero.store.domain.PageModel;
import com.hero.store.domain.Product;
import com.hero.store.service.ProductService;
import com.hero.store.service.serviceimpl.ProductServiceImpl;
import com.hero.store.web.base.BaseServlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProductServlet",value = "/ProductServlet")
public class ProductServlet extends BaseServlet {

    public String findProductByPid(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String pid = req.getParameter("pid");

        ProductService productService = new ProductServiceImpl();
        Product product = productService.findProductByPid(pid);

        req.setAttribute("product", product);

        return "/jsp/product_info.jsp";
    }
        //findProductsByCidWithPage

        public String findProductsByCidWithPage(HttpServletRequest req, HttpServletResponse resp)throws Exception{

            String cid = req.getParameter("cid");
            int curNum = Integer.parseInt(req.getParameter("num"));

            ProductService productService = new ProductServiceImpl();
            PageModel pageModel = productService.findProductsByCidWithPage(cid,curNum);

            req.setAttribute("page",pageModel);
            return "/jsp/product_list.jsp";
        }
}
