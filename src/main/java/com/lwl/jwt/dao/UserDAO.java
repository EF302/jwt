package com.lwl.jwt.dao;

import com.lwl.jwt.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper //在dao层用来@Mapper就不用再启动类上加@MapperScan("com.lwl.dao")
public interface UserDAO {

    //User报红是MyBatis插件导致的，我配置使用了别名，并在xml中使用了别名导致的，不用管，能正常运行
    User login(User user);

}
