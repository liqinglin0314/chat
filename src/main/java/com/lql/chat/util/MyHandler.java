package com.lql.chat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Scope("prototype")
public class MyHandler extends TextWebSocketHandler {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private List<WebSocketSession> list = new ArrayList();

    private boolean flag = false;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<String, String> map = JSONObject.parseObject(payload, HashMap.class);
        if (map.get("text") != null && !map.get("text").equals("")) {
            map.put("date", df.format(new Date()));
            stringRedisTemplate.opsForList().leftPush("record", JSON.toJSONString(map));
        } else {
            list.add(session);
            System.out.println("新的用户登录" + session);
        }
        System.out.println("接收到的数据====>" + map);

        try {
            lunbo(map, session);
        } catch (Exception e) {
            lunbo(map, session);
            e.printStackTrace();
        }
    }

    public void lunbo(Map <String, String> map, WebSocketSession session) throws Exception{

        if(map.get("flag") != null && !map.get("flag").equals("") && map.get("flag").equals("successConnect")){
            Map<String, Object> dataMap = new HashMap<String, Object>();
            JSONArray jsonArray = JSONArray.parseArray(stringRedisTemplate.opsForList().range("record", 0, -1).toString());
            dataMap.put("jsonArray", jsonArray);
            dataMap.put("newOnline", "yes");
            flag = true;
            session.sendMessage(new TextMessage(JSONObject.toJSONString(dataMap)));
        }else{
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).isOpen()) {
                    list.remove(i);
                }
            }

            for (Integer i = 0; i < list.size(); i++) {
                Map<String, Object> dataMap = new HashMap<String, Object>();
                JSONArray jsonArray = JSONArray.parseArray(stringRedisTemplate.opsForList().range("record", 0, -1).toString());
                dataMap.put("jsonArray", jsonArray);
                if(flag){
                    dataMap.put("newOnline", "yes");
                }else{
                    dataMap.put("newOnline", "no");
                }
                list.get(i).sendMessage(new TextMessage(JSONObject.toJSONString(dataMap)));
            }
            flag = false;
        }
    }
}