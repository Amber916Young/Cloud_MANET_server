package com.yang.manet.mapper;

import com.yang.manet.entity.Message;
import org.apache.ibatis.annotations.Delete;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName:MessageMapper
 * @Auther: yyj
 * @Description:
 * @Date: 01/06/2022 23:02
 * @Version: v1.0
 */
public interface MessageMapper {
    List<Message> queryMessage(HashMap<String, Object> map);

    void insertMessage(Message message);

    Message queryMessageByMap(HashMap<String, Object> param);

    @Delete("delete from android.Message where uuid = #{uuid}")
    void deleteMessage(String uuid);

    void updateMessage(HashMap<String, Object> map);

    List<Message> queryMessageByConditions1(HashMap<String, Object> param);

    List<Message> queryMessageByConditions2(HashMap<String, Object> param);

    List<Message> queryMessageByConditions3(HashMap<String, Object> param);

    void updateMessageEntity(Message map);
}
