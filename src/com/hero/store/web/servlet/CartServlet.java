package com.hero.store.web.servlet;

import com.hero.store.domain.Cart;
import com.hero.store.domain.CartItem;
import com.hero.store.domain.Product;
import com.hero.store.service.ProductService;
import com.hero.store.service.serviceimpl.ProductServiceImpl;
import com.hero.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CartServlet",value = "/CartServlet" )
public class CartServlet extends BaseServlet {

    //添加购物项到购物车
    public String addCartItemToCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Cart cart=(Cart)req.getSession().getAttribute("cart");

        if(null == cart){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }

        String pid = req.getParameter("pid");
        int num = Integer.parseInt(req.getParameter("quantity"));

        //通过商品id查询商品对象
        ProductService productService = new ProductServiceImpl();
        Product product = productService.findProductByPid(pid);

        //获取到待购买的购物项
        CartItem cartItem = new CartItem();
        cartItem.setNum(num);
        cartItem.setProduct(product);

        //调用购物车上的方法
        cart.addCartItemToCart(cartItem);

        //重定向到/jsp/cart.jsp
        resp.sendRedirect("/store_v5/jsp/cart.jsp");

        return null;
    }

    //removeCartItem
    public String removeCartItem(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String pid = req.getParameter("id");

        Cart cart = (Cart)req.getSession().getAttribute("cart");

        cart.removeCartItem(pid);

        resp.sendRedirect("/store_v5/jsp/cart.jsp");
        return null;
    }
    //clearCartItem
    public String clearCartItem(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Cart cart = (Cart)req.getSession().getAttribute("cart");

        cart.clearCart();

        resp.sendRedirect("/store_v5/jsp/cart.jsp");
        return null;
    }
}
