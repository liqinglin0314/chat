package com.lql.chat.controller;

import com.alibaba.fastjson.JSON;
import com.lql.chat.pojo.User;
import com.lql.chat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RequestMapping("/ChatAPI")
@Controller
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/getUserLogin", method = RequestMethod.POST)
    @ResponseBody
    public String getUserLogin(@RequestParam String userName, @RequestParam String password){
        User user = userService.getUserLogin(userName, password);
        return JSON.toJSONString(user) + "";
    }

    @PostMapping("/getUserAll")
    @ResponseBody
    public String getUserAll(@RequestParam String arr){
        List<User> list = userService.getUserAll(arr);
        return JSON.toJSONString(list) + "";
    }

    @RequestMapping(value = "/UserRegister", method = RequestMethod.POST)
    @ResponseBody
    public String UserRegister(@RequestParam String userName,@RequestParam String password) {
        Map<String, Object> map = userService.UserRegister(userName, password);
        return JSON.toJSONString(map) + "";
    }

    @RequestMapping(value = "/UserExistence", method = RequestMethod.POST)
    @ResponseBody
    public String UserExistence(String userName) {
        Map<String, Object> map = userService.UserExistence(userName);
        return JSON.toJSONString(map) + "";
    }
}
