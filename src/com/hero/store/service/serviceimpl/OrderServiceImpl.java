package com.hero.store.service.serviceimpl;

import com.hero.store.dao.OrderDao;
import com.hero.store.dao.daoimpl.OrderDaoImpl;
import com.hero.store.domain.Order;
import com.hero.store.domain.OrderItem;
import com.hero.store.domain.PageModel;
import com.hero.store.domain.User;
import com.hero.store.service.OrderService;
import com.hero.store.utils.BeanFactory;
import com.hero.store.utils.JDBCUtils;

import java.sql.Connection;
import java.util.List;


public class OrderServiceImpl implements OrderService {

    OrderDao orderDao = new OrderDaoImpl();
    @Override
    public void saveOrder(Order order) throws Exception {
        //保存订单和订单下所有的订单项（同时成功 or 失败）
        Connection conn = null;

        try {
            //获取连接
            conn = JDBCUtils.getConnection();
            //开启事务
            conn.setAutoCommit(false);
            //保存订单
            orderDao.saveOrder(conn,order);
            //保存订单项
            for (OrderItem item:order.getList()) {
                orderDao.saveOrderItem(conn,item);
            }
            //提交
            conn.commit();
        } catch (Exception e) {
            //事务回滚
            conn.rollback();
        }

    }

    @Override
    public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception {

        //1、创建PageModel对象，目的：计算且携带分页参数
        //select count(*) from orders where uid=?
        int totalRecords = orderDao.getTotalRecords(user);
        PageModel pm = new PageModel(curNum,totalRecords,3);
        //2、关联集合
        List list = orderDao.findMyOrdersWithPage(user,pm.getStartIndex(),pm.getPageSize());
        pm.setList(list);
        //3、关联url
        pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
        return pm;
    }

    @Override
    public Order findOrderByOid(String oid) throws Exception {

        return orderDao.findOrderByOid(oid);
    }

    @Override
    public void updateOrder(Order order) throws Exception {
        orderDao.updateOrder(order);
    }

    @Override
    public List<Order> findAllOrders() throws Exception {
        return orderDao.findAllOrders();
    }

    @Override
    public List<Order> findAllOrders(String st) throws Exception {
        return orderDao.findAllOrders(st);
    }
}
