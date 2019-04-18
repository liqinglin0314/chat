package com.lql.chat.service;

import com.lql.chat.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User getUserLogin(String userName, String password);

    List<User> getUserAll(String arr);

    Map<String, Object> UserRegister(String userName, String password);

    Map<String, Object> UserExistence(String userName);
}
