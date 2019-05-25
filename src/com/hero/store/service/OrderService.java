package com.hero.store.service;

import com.hero.store.domain.Order;
import com.hero.store.domain.PageModel;
import com.hero.store.domain.User;

import java.util.List;

public interface OrderService {
    void saveOrder(Order order)throws Exception;

    PageModel findMyOrdersWithPage(User user, int curNum)throws Exception;

    Order findOrderByOid(String oid)throws Exception;

    void updateOrder(Order order)throws Exception;

    List<Order> findAllOrders()throws Exception;

    List<Order> findAllOrders(String st)throws Exception;
}
