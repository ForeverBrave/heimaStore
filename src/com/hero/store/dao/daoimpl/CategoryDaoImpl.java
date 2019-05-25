package com.hero.store.dao.daoimpl;

import com.hero.store.dao.CategoryDao;
import com.hero.store.domain.Category;
import com.hero.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> getAllCats() throws Exception {
        String sql = "select * from category";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql,new BeanListHandler<Category>(Category.class));
    }

    @Override
    public void addCategory(Category category) throws Exception {
        String sql = "insert into category values (?,?)";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        queryRunner.update(sql,category.getCid(),category.getCname());
    }
}
