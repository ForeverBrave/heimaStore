package com.hero.store.dao;

import com.hero.store.domain.User;

import java.sql.SQLException;

public interface UserDao {
    User userActive(String code) throws SQLException ;

    void updateUser(User user) throws SQLException ;

    void userRegist(User user) throws SQLException ;

    User userLogin(User user) throws SQLException ;
}
