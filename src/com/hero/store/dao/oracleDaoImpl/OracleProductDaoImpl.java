package com.hero.store.dao.oracleDaoImpl;

import com.hero.store.dao.ProductDao;
import com.hero.store.domain.Product;

import java.util.List;

public class OracleProductDaoImpl implements ProductDao {
    @Override
    public List<Product> findHots() throws Exception {
        return null;
    }

    @Override
    public List<Product> findNews() throws Exception {
        return null;
    }

    @Override
    public Product findProductByPid(String pid) throws Exception {
        return null;
    }

    @Override
    public int findTotalRecords(String cid) throws Exception {
        return 0;
    }

    @Override
    public List findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws Exception {
        return null;
    }

    @Override
    public int findTotalRecords() throws Exception {
        return 0;
    }

    @Override
    public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws Exception {
        return null;
    }

    @Override
    public void saveProduct(Product product) throws Exception {

    }
}
