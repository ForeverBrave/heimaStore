package com.hero.store.service;

import com.hero.store.domain.User;

import java.sql.SQLException;

public interface UserService{

    void userRegist(User user) throws SQLException;

    boolean userActive(String code) throws SQLException;

    User userLogin(User user) throws SQLException;
}
