package com.hero.store.service.serviceimpl;

import com.hero.store.dao.CategoryDao;
import com.hero.store.dao.daoimpl.CategoryDaoImpl;
import com.hero.store.domain.Category;
import com.hero.store.service.CategoryService;
import com.hero.store.utils.BeanFactory;
import com.hero.store.utils.JedisUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    CategoryDao categoryDao = (CategoryDao) BeanFactory.createObject("CategoryDao");
    @Override
    public List<Category> getAllCats() throws Exception {
        return categoryDao.getAllCats();
    }

    @Override
    public void addCategory(Category category) throws Exception {
        //向mysql插入一条数据
        categoryDao.addCategory(category);
        //更新redis缓存
        Jedis jedis = JedisUtils.getJedis();
        jedis.del("allCats");
        JedisUtils.closeJedis(jedis);
    }
}
