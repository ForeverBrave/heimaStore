package com.hero.store.web.servlet;

import com.hero.store.domain.Category;
import com.hero.store.service.CategoryService;
import com.hero.store.service.serviceimpl.CategoryServiceImpl;
import com.hero.store.utils.JedisUtils;
import com.hero.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.hero.store.utils.JedisUtils.getJedis;

@WebServlet(name = "CategoryServlet",value = "/CategoryServlet")
public class CategoryServlet extends BaseServlet {

    //findAllCats
    public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //在redis中获取全部分类信息
        Jedis jedis = JedisUtils.getJedis();
        String jsonStr = jedis.get("allCats");
        if (null == jsonStr || "".equals(jsonStr)){
            //调用业务层获取全部分类
            CategoryService categoryService = new CategoryServiceImpl();
            List<Category> list = categoryService.getAllCats();
            //将全部分类转换为JSON格式的数据
            jsonStr = JSONArray.fromObject(list).toString();
            //将获取到的JSON格式的数据存入到redis
            jedis.set("allCats",jsonStr);
            //System.out.println("redis缓存中没有数据");

            //将全部分类信息响应到客户端
            //告诉浏览器本次响应的数据是JSON格式的字符串
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().print(jsonStr);

        }else {
            //System.out.println("redis缓存中有数据");
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().print(jsonStr);

        }

        JedisUtils.closeJedis(jedis);

        return null;
    }
}
