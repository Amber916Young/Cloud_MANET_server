package com.yang.manet.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yang.manet.Utils.TimeParse;
import com.yang.manet.entity.Message;
import com.yang.manet.service.MessageService;
import com.yang.manet.Utils.AJAXReturn;
import com.yang.manet.Utils.JsonUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName:CommunicationController
 * @Auther: yyj
 * @Description:
 * @Date: 31/05/2022 19:07
 * @Version: v1.0
 */
@RestController
@RequestMapping("/message")
public class CommunicationController {
    @Autowired
    MessageService messageService;

    @SneakyThrows
    @ResponseBody
    @GetMapping("/viewAll")
    public AJAXReturn viewAll() {
        List<Message> list = messageService.queryMessage(new HashMap<>());
        return AJAXReturn.success("query message successfully",list);
    }

    @SneakyThrows
    @ResponseBody
    @GetMapping("/deleteAll")
    public AJAXReturn deleteAll() {
        List<Message> list = messageService.queryMessage(new HashMap<>());
        for (Message message : list){
            messageService.deleteMessage(message.getUuid());
        }
        return AJAXReturn.success("delete message successfully");
    }


    @SneakyThrows
    @ResponseBody
    @PostMapping("/getUnreadMessage")
    public AJAXReturn getUnreadMessage(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map= JSONObject.parseObject(jsonData, HashMap.class);
        List<Message> list = messageService.queryMessage(map);
        HashMap<String, String> res = new HashMap<>();
        res.put("msgList", JsonUtils.objectToJson(list));
        for(Message info : list){
            messageService.deleteMessage(info.getUuid());
        }
        return AJAXReturn.success(JsonUtils.objectToJson(res));
    }

    @SneakyThrows
    @ResponseBody
    @PostMapping("/updateMessage")
    public AJAXReturn updateMessage(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map= JSONObject.parseObject(jsonData, HashMap.class);
        messageService.updateMessage(map);
        return AJAXReturn.success();
    }


    @SneakyThrows
    @ResponseBody
    @PostMapping("/deleteUnreadMessage")
    public AJAXReturn deleteUnreadMessage(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map= JSONObject.parseObject(jsonData, HashMap.class);
        String uuid = map.get("uuid").toString();
        messageService.deleteMessage(uuid);
        return AJAXReturn.success();
    }

    @SneakyThrows
    @ResponseBody
    @PostMapping("/queryFromCloud")
    public AJAXReturn queryFromCloud(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map= JSONObject.parseObject(jsonData, HashMap.class);
        String localName = map.get("localName").toString();
        String connectedDevice = map.get("connectedDevice").toString();
        HashMap<String, Object> param = new HashMap<>();
        List<Message> mapList = new ArrayList<>();
        List<String> ans = new ArrayList<>();

        if(map.containsKey("flag") && map.get("flag").equals("Sourceunread")){
            param.put("sourceName",connectedDevice);
            param.put("targetName",localName);
            param.put("isRead","0");
            param.put("readDate","END");
            mapList= messageService.queryMessageByConditions1(param);
            for(Message tmp : mapList){
                String string = JsonUtils.objectToJson(tmp);
                ans.add(string);
            }
            return AJAXReturn.success(ans);
        }
        if(map.containsKey("flag") && map.get("flag").equals("Targetunread")){
            param.put("targetName",connectedDevice);
            param.put("sourceName",localName);
            param.put("readDate","END");
            mapList= messageService.queryMessageByConditions2(param);
            for(Message tmp : mapList){
                String string = JsonUtils.objectToJson(tmp);
                ans.add(string);
            }
        }
        return AJAXReturn.success(ans);

    }
    @SneakyThrows
    @ResponseBody
    @PostMapping("/feedbackMessage")
    public AJAXReturn feedbackMessage(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map= JSONObject.parseObject(jsonData, HashMap.class);
        String listStr = map.get("messageList").toString().replaceAll("\\[","").replaceAll("\\]","");
        listStr = "["+listStr+"]";
        String isRead = map.get("isRead").toString();
        JSONArray jsonArray =  JSONObject.parseArray(listStr);
        for(int i =0;i<jsonArray.size();i++){
            JSONObject jb = jsonArray.getJSONObject(i);
            Message tmp = JSON.toJavaObject(jb,Message.class);
            HashMap<String, Object> param = new HashMap<>();
            param.put("uuid",tmp.getUuid());
            param.put("isRead",isRead);
            param.put("readDate",tmp.getReadDate());
            messageService.updateMessage(param);
        }
        return AJAXReturn.success();
    }


    @SneakyThrows
    @ResponseBody
    @PostMapping("/sendUnreadMessageTwice")
    public AJAXReturn sendUnreadMessageTwice(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map= JSONObject.parseObject(jsonData, HashMap.class);
        Message isExist = messageService.queryMessageByMap(map);
        if(isExist != null){
            messageService.deleteMessage(isExist.getUuid());
        }
        return AJAXReturn.success();
    }
    @SneakyThrows
    @ResponseBody
    @PostMapping("/cloud/sendUnreadMessage")
    public AJAXReturn cloudsendUnreadMessage(@RequestBody String jsonData) {
//        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        Message map = JSONObject.parseObject(jsonData, Message.class);
//        String uploadName = map.get("uploadName").toString();
//        String uploadMAC = map.get("uploadMAC").toString();
//        String uploadTime = map.get("uploadTime").toString();
//        String sendDate = map.get("sendDate").toString();
//        String readDate = map.get("readDate").toString();
//        String targetName = map.get("targetName").toString();
//        String targetMAC = map.get("targetMAC").toString();
//        String sourceName = map.get("sourceName").toString();
//        String sourceMAC = map.get("sourceMAC").toString();
//        String uuid = map.get("uuid").toString();
//        String content = map.get("content").toString();
//        String isRead = map.get("isRead").toString();
        HashMap<String, Object> param = new HashMap<>();
        param.put("uuid", map.getUuid());
        map.setIsUpload(1);
        Message isExist = messageService.queryMessageByMap(param);
        if (map.getReadDate().equals("END")) map.setIsRead(0);
        else map.setIsRead(1);

        if (isExist == null) {
            messageService.insertMessage(map);
        } else {
            messageService.updateMessageEntity(map);
        }

        return AJAXReturn.success();
    }

    @SneakyThrows
    @ResponseBody
    @PostMapping("/sendUnreadMessage")
    public AJAXReturn sendUnreadMessage(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map= JSONObject.parseObject(jsonData, HashMap.class);
        String uploadName = map.get("uploadName").toString();
        TimeParse timeParse = new TimeParse();
        if (map.containsKey("messageList")) {
            String uploadMAC = map.get("uploadMAC").toString();
            String uploadTime = map.get("uploadTime").toString();
            String listStr = map.get("messageList").toString().replaceAll("\\[","").replaceAll("\\]","");
            listStr = "["+listStr+"]";
            JSONArray jsonArray =  JSONObject.parseArray(listStr);
            for(int i =0;i<jsonArray.size();i++){
                JSONObject jb = jsonArray.getJSONObject(i);
                Message tmp = JSON.toJavaObject(jb,Message.class);
                tmp.setUploadMAC(uploadMAC);
                tmp.setUploadName(uploadName);
                tmp.setIsUpload(1);
                tmp.setUploadTime(uploadTime);
                HashMap<String, Object> param = new HashMap<>();
                param.put("uuid",tmp.getUuid());
                Message isExist = messageService.queryMessageByMap(param);
                if(isExist==null){
                    messageService.insertMessage(tmp);
                }
            }
        }
        if(uploadName != null){
            map = new HashMap<>();
            map.put("targetName",uploadName);
            map.put("isRead",0);
            List<Message> list = messageService.queryMessageByConditions3(map);
            HashMap<String, String> res = new HashMap<>();
            // 自己的未读消息
            if(list.size()!=0){
                for(Message message : list){
                    String time = timeParse.convertDate2String(new Date(),"yyyy-MM-dd HH:mm:ss");
                    message.setReadDate(time);
                    message.setIsRead(1);
                    HashMap<String, Object> update = new HashMap<>();
                    update.put("readDate",time);
                    update.put("isRead",1);
                    update.put("uuid",message.getUuid());
                    messageService.updateMessage(update);
                }
                res.put("mine",JsonUtils.objectToJson(list));
            }
            map = new HashMap<>();
            map.put("sourceName",uploadName);
            map.put("isRead",1);
            map.put("readDate","END");
            List<Message> list2 = messageService.queryMessageByConditions3(map);
            // 发送给别人的消息 检查有没有接收
            if(list2.size()!=0){
                res.put("other",JsonUtils.objectToJson(list2));
                for(Message message : list2){
                    messageService.deleteMessage(message.getUuid());
                }
            }


            if(res.size() == 0){
                return AJAXReturn.success();
            }
            return AJAXReturn.success("",JsonUtils.objectToJson(res));
        }
        return AJAXReturn.success();
    }


}
