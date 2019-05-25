package com.hero.store.service.serviceimpl;

import com.hero.store.dao.UserDao;
import com.hero.store.dao.daoimpl.UserDaoImpl;
import com.hero.store.domain.User;
import com.hero.store.service.UserService;
import com.hero.store.utils.BeanFactory;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    UserDao userDao = (UserDao) BeanFactory.createObject("UserDao");

    @Override
    public void userRegist(User user) throws SQLException {
        userDao.userRegist(user);
    }

    @Override
    public boolean userActive(String code) throws SQLException {
        //实现注册功能
        //对DButil发送select * from user where code = ？
        User user = userDao.userActive(code);

        if(user != null){
            //如可以根据激活码查询到用户
            //则修改用户状态，并清除激活码
            user.setState(1);
            user.setCode(null);
            //对数据库执行一次真实的更新操作
            userDao.updateUser(user);
            return true;
        }else {
            //如不能根据激活码查询到一个用户则返回false
            return false;
        }
    }

    @Override
    public User userLogin(User user) throws SQLException {

        User uu = userDao.userLogin(user);

        if(null == uu){
            throw new RuntimeException("密码有误！");
        }else if(uu.getState()==0){
            throw new RuntimeException("密码有误！");
        }else {
            return uu;
        }
    }
}
