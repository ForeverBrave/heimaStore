package com.hero.store.test;

import com.hero.store.domain.OrderItem;
import com.hero.store.domain.Product;
import com.hero.store.utils.JDBCUtils;
import com.hero.store.utils.MyBeanUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestMapListHandler {

    @Test
    public void test00()throws Exception {

        List<OrderItem> list01 = new ArrayList<OrderItem>();

        String sql = "SELECT * FROM orderitem o,product p WHERE o.`pid`=p.`pid` AND oid='AACF744794404DF0A65118526C71FBE9'";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler());

        for (Map<String, Object> map : list) {

            for (Map.Entry<String,Object> entry : map.entrySet())
                System.out.print(entry.getKey()+":"+entry.getValue()+"    ");
                System.out.println();
        }

    }
}
