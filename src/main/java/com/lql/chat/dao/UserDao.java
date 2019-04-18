package com.lql.chat.dao;

import com.lql.chat.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    User getUserLogin(@Param("userName") String userName, @Param("password") String password);

    List<User> getUserAll(@Param("list") List<Integer> list);

    Integer UserRegister(@Param("userName") String userName, @Param("password") String password);

    Integer UserExistence(@Param("userName") String userName);
}
