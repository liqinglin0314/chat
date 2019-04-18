package com.lql.chat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.lql.chat.dao.UserDao;
import com.lql.chat.pojo.User;
import com.lql.chat.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User getUserLogin(String userName, String password) {

        return userDao.getUserLogin(userName, password);
    }

    @Override
    public List<User> getUserAll(String arr) {
        JSONArray jsonArray = JSONArray.parseArray(arr);
        List<Integer> list = new ArrayList<Integer>();
        for(Integer i = 0; i < jsonArray.size(); i++){
            list.add(Integer.parseInt(jsonArray.get(i).toString()));
        }
        return userDao.getUserAll(list);
    }

    @Override
    @Transactional
    public Map<String, Object> UserRegister(String userName, String password) {
        Integer flag = userDao.UserRegister(userName, password);
        Map<String, Object> map = new HashMap<String, Object>();
        if(flag > 0){
            map.put("code", 200);
            map.put("msg", "注册成功！");
        }else{
            map.put("code", 400);
            map.put("msg", "注册失败！");
        }
        return map;
    }

    @Override
    public Map<String, Object> UserExistence(String userName) {
        Integer flag = userDao.UserExistence(userName);
        Map<String, Object> map = new HashMap<String, Object>();
        if(flag > 0){
            map.put("code", 200);
            map.put("msg", "用户存在！");
        }else{
            map.put("code", 400);
            map.put("msg", "用户不存在！");
        }
        return map;
    }
}
