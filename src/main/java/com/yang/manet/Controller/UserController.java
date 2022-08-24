package com.yang.manet.Controller;

import com.alibaba.fastjson.JSONObject;
import com.yang.manet.Utils.RandomID;
import com.yang.manet.Utils.TimeParse;
import com.yang.manet.entity.DeviceInfo;
import com.yang.manet.entity.MANETInfo;
import com.yang.manet.service.MANETService;
import com.yang.manet.service.UserService;
import com.yang.manet.Utils.AJAXReturn;
import com.yang.manet.Utils.JsonUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName:UserController
 * @Auther: yyj
 * @Description:
 * @Date: 11/06/2022 18:22
 * @Version: v1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MANETService manetService;
    //login

    @SneakyThrows
    @ResponseBody
    @PostMapping("/login")
    public AJAXReturn loginUser(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map= JSONObject.parseObject(jsonData, HashMap.class);
        DeviceInfo deviceInfo = userService.queryUserInfo(map);
        if(deviceInfo==null) return AJAXReturn.unauth();
        map = new HashMap<>();
        map.put("uuid",deviceInfo.getUuid());
        HashMap<String,Object> mid = manetService.queryMANET_memberByMap(map);
        if(deviceInfo!=null){
            String ans = "";
            if(mid == null){
                deviceInfo.setMANET_UUID("none");
                deviceInfo.setRole("member");
                ans = JsonUtils.objectToJson(deviceInfo);
                return AJAXReturn.success("Login successfully without any group",ans);
            }
            deviceInfo.setMANET_UUID(mid.get("MANET_UUID").toString());

//            List<MANETInfo> manetInfo = manetService.queryAllMANET();
//            if(manetInfo.getOwnerID().equals(deviceInfo.getUuid())){
//                deviceInfo.setRole("owner");
//            }else {
//                deviceInfo.setRole("member");
//            }

            deviceInfo.setRole("member");
            ans = JsonUtils.objectToJson(deviceInfo);
            return AJAXReturn.success("Login successfully",ans);
        }
        return AJAXReturn.error("Password or Login name is not correct");
    }
    @SneakyThrows
    @ResponseBody
    @PostMapping("/register")
    public AJAXReturn registerUser(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map= JSONObject.parseObject(jsonData, HashMap.class);
        TimeParse timeParse = new TimeParse();
        String time = timeParse.convertDate2String(new Date(),"yyyy-MM-dd HH:mm:ss");
        map.put("loginDate",time);
        map.put("registerDate",time);
        map.put("status",1);
        map.put("uuid", String.valueOf(RandomID.genIDWorker()));
        userService.registerUser(map);
        return AJAXReturn.success("Register successfully");
    }


    @SneakyThrows
    @ResponseBody
    @GetMapping("/viewALL")
    public AJAXReturn viewALL() {
        List<HashMap> list = userService.queryAllUsers();
        return AJAXReturn.success("successfully",list);
    }

    @SneakyThrows
    @ResponseBody
    @PostMapping("/update")
    public AJAXReturn updateUser(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map= JSONObject.parseObject(jsonData, HashMap.class);
        DeviceInfo deviceInfo = userService.queryUserInfo(map);
        return AJAXReturn.success("",deviceInfo);
    }



}
