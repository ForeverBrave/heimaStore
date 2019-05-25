package com.hero.store.service;

import com.hero.store.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCats()throws Exception;

    void addCategory(Category category)throws Exception;
}
