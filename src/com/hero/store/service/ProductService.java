package com.hero.store.service;

import com.hero.store.domain.PageModel;
import com.hero.store.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> findHots()throws Exception;

    List<Product> findNews()throws Exception;

    Product findProductByPid(String pid)throws Exception;

    PageModel findProductsByCidWithPage(String cid, int curNum)throws Exception;

    PageModel findAllProductsWithPage(int curNum)throws Exception;

    void saveProduct(Product product)throws Exception;
}
