package com.hero.store.service.serviceimpl;


import com.hero.store.dao.ProductDao;
import com.hero.store.dao.daoimpl.ProductDaoImpl;
import com.hero.store.domain.PageModel;
import com.hero.store.domain.Product;
import com.hero.store.service.ProductService;
import com.hero.store.utils.BeanFactory;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    ProductDao productDao = (ProductDao) BeanFactory.createObject("ProductDao");

    @Override
    public Product findProductByPid(String pid) throws Exception {
        return productDao.findProductByPid(pid);
    }

    @Override
    public List<Product> findHots()throws Exception {
        return productDao.findHots();
    }

    @Override
    public List<Product> findNews()throws Exception {
        return productDao.findNews();
    }

    @Override
    public PageModel findProductsByCidWithPage(String cid, int curNum)throws Exception {

        //1、创建PageModel  目的：计算分页参数

        //统计当前分类下的商品个数
        int totalRecords =  productDao.findTotalRecords(cid);
        PageModel pageModel = new PageModel(curNum,totalRecords,12);
        //2、关联集合
        List list = productDao.findProductsByCidWithPage(cid,pageModel.getStartIndex(),pageModel.getPageSize());
        //3、关联url
        pageModel.setList(list);
        pageModel.setUrl("ProductServlet?method=findProductsByCidWithPage&cid="+cid);
        return pageModel;
    }

    @Override
    public PageModel findAllProductsWithPage(int curNum) throws Exception {
        //创建分页三步走：
        //1.创建对象
        int totalRecords = productDao.findTotalRecords();
        PageModel pageModel = new PageModel(curNum,totalRecords,5);
        //2.关联集合
        List<Product> list = productDao.findAllProductsWithPage(pageModel.getStartIndex(),pageModel.getPageSize());
        pageModel.setList(list);
        //3.关联url
        pageModel.setUrl("AdminProductServlet?method=findAllProductsWithPage");
        return pageModel;
    }

    @Override
    public void saveProduct(Product product) throws Exception {
        productDao.saveProduct(product);
    }
}
