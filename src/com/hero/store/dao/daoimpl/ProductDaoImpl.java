package com.hero.store.dao.daoimpl;

import com.hero.store.dao.ProductDao;
import com.hero.store.domain.Product;
import com.hero.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

public class ProductDaoImpl implements ProductDao {

    @Override
    public Product findProductByPid(String pid) throws Exception {
        String sql = "select * from product where pid=?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql,new BeanHandler<Product>(Product.class),pid);

    }

    @Override
    public List<Product> findHots()throws Exception {
        String sql = "select * from product where pflag=0 and is_hot=1 order by pdate desc limit 0,9";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql,new BeanListHandler<Product>(Product.class));

    }

    @Override
    public List<Product> findNews()throws Exception {
        String sql = "select * from product where pflag=0 order by pdate desc limit 0,9";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql,new BeanListHandler<Product>(Product.class));

    }

    @Override
    public int findTotalRecords(String cid)throws Exception {

        String sql = "select count(*) from product where cid = ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Long num = (Long) queryRunner.query(sql,new ScalarHandler(),cid);
        return num.intValue();
    }

    @Override
    public List findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws Exception {

        String sql = "select * from product where cid = ? limit ? , ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql,new BeanListHandler<Product>(Product.class),cid,startIndex,pageSize);

    }

    @Override
    public int findTotalRecords() throws Exception {
        String sql = "select count(*) from product";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Long num = (Long) queryRunner.query(sql,new ScalarHandler());
        return num.intValue();
    }

    @Override
    public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws Exception {
        String sql ="select * from product order by pdate desc limit  ? , ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql,new BeanListHandler<Product>(Product.class),startIndex,pageSize);
    }

    @Override
    public void saveProduct(Product product) throws Exception {
        String sql="INSERT INTO product VALUES(?,?,?,?,?,?,?,?,?,?)";
        QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
        Object[] params={product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid()};
        qr.update(sql,params);
    }
}
