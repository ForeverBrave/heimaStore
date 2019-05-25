package com.hero.store.dao;

import com.hero.store.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAllCats() throws Exception;

    void addCategory(Category category) throws Exception;
}
