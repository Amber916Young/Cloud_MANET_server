package com.yang.manet.service;

import com.yang.manet.entity.Message;
import com.yang.manet.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName:MessageService
 * @Auther: yyj
 * @Description:
 * @Date: 01/06/2022 23:02
 * @Version: v1.0
 */
@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapperr;

    public List<Message> queryMessage(HashMap<String, Object> map) {
        return messageMapperr.queryMessage(map);
    }

    public void insertMessage(Message message) {
        messageMapperr.insertMessage(message);
    }

    public Message queryMessageByMap(HashMap<String, Object> param) {
        return  messageMapperr.queryMessageByMap(param);
    }

    public void deleteMessage(String uuid) {
        messageMapperr.deleteMessage(uuid);
    }

    public void updateMessage(HashMap<String, Object> map) {
        messageMapperr.updateMessage(map);
    }

    public List<Message> queryMessageByConditions1(HashMap<String, Object> param) {
        return messageMapperr.queryMessageByConditions1(param);
    }

    public List<Message> queryMessageByConditions2(HashMap<String, Object> param) {
        return messageMapperr.queryMessageByConditions2(param);
    }

    public List<Message> queryMessageByConditions3(HashMap<String, Object> param) {
        return messageMapperr.queryMessageByConditions3(param);
    }

    public void updateMessageEntity(Message map) {
        messageMapperr.updateMessageEntity(map);
    }
}
