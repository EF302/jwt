package com.lwl.jwt.service;

import com.lwl.jwt.dao.UserDAO;
import com.lwl.jwt.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;

    @Override
    //如果当前环境有事务，就加入到当前事务；如果没有事务，就以非事务的方式执行。从这个说明来看，使用这个级别和不加@Transaction注解也没什么不一样
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user) {
        User userDB = userDAO.login(user);
        if (userDB != null) {
            return userDB;
        }
        throw new RuntimeException("认证失败");
    }
}
