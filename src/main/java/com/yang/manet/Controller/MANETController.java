package com.yang.manet.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yang.manet.entity.DeviceInfo;
import com.yang.manet.entity.MANETInfo;
import com.yang.manet.service.MANETService;
import com.yang.manet.Utils.AJAXReturn;
import com.yang.manet.Utils.JsonUtils;
import com.yang.manet.Utils.RandomID;
import com.yang.manet.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName:MANETController
 * @Auther: yyj
 * @Description: Operation of MANET
 * @Date: 13/05/2022 10:05
 * @Version: v1.0
 */
@RequestMapping("/MANET")
@RestController
public class MANETController {


    @Autowired
    UserService userService;

    @Autowired
    MANETService manetService;

    @SneakyThrows
    @ResponseBody
    @PostMapping("/create")
    public AJAXReturn createMANET(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        JSONObject map = JSONObject.parseObject(jsonData, JSONObject.class);
        String MANET_uuid = RandomID.getRandomOrderNumber();
        String ownerID = map.get("uuid").toString();
        HashMap<String , Object> param = new HashMap<>();
        param.put("ownerID",ownerID);
        MANETInfo isExist = manetService.queryMANET(param);
        if(isExist != null ){
            return AJAXReturn.warn("You have already create a MANET instance");
        }
        param = new HashMap<>();
        param.put("ownerID",ownerID);
        param.put("uuid",MANET_uuid);
        // number
        manetService.insertMANET(param);
        param = new HashMap<>();
        param.put("MANET_UUID",MANET_uuid);
        param.put("uuid",ownerID);
        manetService.insertMANETmember(param);
        if(map.getJSONArray("items") != null){
            JSONArray jsonArray = map.getJSONArray("items") ;
            JSONArray ja = null;
            for (int i = 0;i< jsonArray.size();i++) {
                ja = jsonArray.getJSONArray(i);
                JSONObject jsonObject = ja.getJSONObject(0) ;
                String name = jsonObject.getString("name");
                String MAC = jsonObject.getString("MAC");
                param.put("username",name);
                DeviceInfo deviceInfo = userService.queryUserInfo(param);
                deviceInfo.setMANET_UUID(MAC);
                deviceInfo.setUsername(name);
                param.put("uuid",deviceInfo.getUuid());
                userService.updateUserInfo(deviceInfo);
                manetService.insertMANETmember(param);
            }
        }
        return AJAXReturn.success("",MANET_uuid);
    }


    @SneakyThrows
    @ResponseBody
    @GetMapping("/viewAll")
    public AJAXReturn viewAll() {
        List<HashMap<String, Object>> list = manetService.queryMANET_memberTable();
        return AJAXReturn.success(list);
    }

    @SneakyThrows
    @ResponseBody
    @PostMapping("/join")
    public AJAXReturn joinMANET(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map= JSONObject.parseObject(jsonData, HashMap.class);
        String MANET_UUID = map.get("MANET_UUID").toString();
        String uuid = map.get("uuid").toString();
        map = new HashMap<>();
        map.put("ownerID",uuid);
        MANETInfo manetInfo = manetService.queryMANET(map);
        if(manetInfo != null){
            return AJAXReturn.warn("already in a MANET");
        }
        map = new HashMap<>();
        map.put("uuid",uuid);
        manetService.deleteMANET_member(map);
        map.put("MANET_UUID",MANET_UUID);
        manetService.insertMANETmember(map);
        return AJAXReturn.success();
    }


    //membership
    @SneakyThrows
    @ResponseBody
    @PostMapping("/membership")
    public AJAXReturn membership(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map= JSONObject.parseObject(jsonData, HashMap.class);
        List<DeviceInfo> list = manetService.queryMANET_member(map);
        List<HashMap<String ,Object>> own = new ArrayList();
        HashMap<String, Object> param = new HashMap<>();
        param.put("uuid", map.get("uuid"));
        String sss = "";
        param.put("list", list);
        own.add(param);
        List<MANETInfo> manetInfos = manetService.queryMANETList(map);
        List<HashMap<String ,Object>> other = new ArrayList();
        for(MANETInfo manetInfo : manetInfos) {
            param = new HashMap<>();
            param.put("uuid", manetInfo.getUuid());
            List<DeviceInfo> tmp = manetService.queryMANET_member(param);
            param.put("list", tmp);
            other.add(param);
        }
        HashMap<String, Object> ans = new HashMap<>();
        String ownS = JsonUtils.objectToJson(own);
        String otherS = JsonUtils.objectToJson(other);

        ans.put("own",ownS);
        ans.put("other",otherS);
        sss = JsonUtils.objectToJson(ans);
        return AJAXReturn.success("",sss);
    }

}
