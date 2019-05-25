package com.hero.store.dao.daoimpl;

import com.hero.store.dao.OrderDao;
import com.hero.store.domain.Order;
import com.hero.store.domain.OrderItem;
import com.hero.store.domain.Product;
import com.hero.store.domain.User;
import com.hero.store.utils.JDBCUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void saveOrder(Connection conn, Order order) throws Exception {
        String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
        QueryRunner queryRunner = new QueryRunner();
        Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
        queryRunner.update(conn,sql,params);
    }

    @Override
    public void saveOrderItem(Connection conn, OrderItem item) throws Exception {
        String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";
        QueryRunner queryRunner = new QueryRunner();
        Object[] params = {item.getItemid(),item.getQuantity(),item.getTotal(),item.getProduct().getPid(),item.getOrder().getOid()};
        queryRunner.update(conn,sql,params);
    }

    @Override
    public int getTotalRecords(User user) throws Exception {

        String sql = "select count(*) from orders where uid=?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Long num = (Long) queryRunner.query(sql,new ScalarHandler(),user.getUid());
        return num.intValue();
    }

    @Override
    public List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception {
        String sql = "SELECT * FROM orders WHERE uid=? limit ?,?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        List<Order> list =queryRunner.query(sql,new BeanListHandler<Order>(Order.class),user.getUid(),startIndex,pageSize);

        //遍历所有订单
        for(Order order : list){
            //获取到每笔订单oid   查询每笔订单瞎的订单项以及订单项对应的商品信息
            String oid = order.getOid();
            sql = "select * from orderItem o,product p where o.pid=p.pid and oid=?";
            List<Map<String,Object>> list02 = queryRunner.query(sql,new MapListHandler(),oid);
            //遍历list
            for (Map<String,Object> map: list02) {
                OrderItem orderItem = new OrderItem();
                Product product = new Product();
                // 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
                // 1_创建时间类型的转换器
                DateConverter dt = new DateConverter();
                // 2_设置转换的格式
                dt.setPattern("yyyy-MM-dd");
                // 3_注册转换器
                ConvertUtils.register(dt, java.util.Date.class);

                //将map中属于orderItem的数据自动填充到orderItem对象上
                BeanUtils.populate(orderItem,map);
                BeanUtils.populate(product,map);

                //让每个订单项和商品发生关联关系
                orderItem.setProduct(product);
                //将每个订单项存入订单下的集合中
                order.getList().add(orderItem);

            }
        }
        return list;
    }

    @Override
    public Order findOrderByOid(String oid) throws Exception {

        String sql = "select * from orders where oid=?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Order order = queryRunner.query(sql,new BeanHandler<Order>(Order.class),oid);

        sql = "select * from orderItem o,product p where o.pid=p.pid and oid=?";
        List<Map<String,Object>> list02 = queryRunner.query(sql,new MapListHandler(),oid);
        //遍历list
        for (Map<String,Object> map: list02) {
            OrderItem orderItem = new OrderItem();
            Product product = new Product();
            // 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
            // 1_创建时间类型的转换器
            DateConverter dt = new DateConverter();
            // 2_设置转换的格式
            dt.setPattern("yyyy-MM-dd");
            // 3_注册转换器
            ConvertUtils.register(dt, java.util.Date.class);

            //将map中属于orderItem的数据自动填充到orderItem对象上
            BeanUtils.populate(orderItem,map);
            BeanUtils.populate(product,map);

            //让每个订单项和商品发生关联关系
            orderItem.setProduct(product);
            //将每个订单项存入订单下的集合中
            order.getList().add(orderItem);
        }
        return order;
    }

    @Override
    public void updateOrder(Order order) throws Exception {
        String sql = "update orders set ordertime=? ,total=? ,state=? ,address=? ,name=? ,telephone=? where oid=?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid()};
        queryRunner.update(sql,params);
    }

    @Override
    public List<Order> findAllOrders() throws Exception {
        String sql = "select * from orders";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql,new BeanListHandler<Order>(Order.class));
    }

    @Override
    public List<Order> findAllOrders(String st) throws Exception {

        String sql = "select * from orders where state = ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql,new BeanListHandler<Order>(Order.class),st);

    }
}
