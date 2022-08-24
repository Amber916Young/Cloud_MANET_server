package com.yang.manet.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yang.manet.Utils.AJAXReturn;
import com.yang.manet.Utils.Graph;
import com.yang.manet.Utils.JsonUtils;
import com.yang.manet.Utils.RandomID;
import com.yang.manet.entity.*;
import com.yang.manet.service.MANETService;
import com.yang.manet.service.MessageService;
import com.yang.manet.service.RouterService;
import com.yang.manet.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Stream;

/**
 * @ClassName:RouterController
 * @Auther: yyj
 * @Description:
 * @Date: 24/06/2022 00:20
 * @Version: v1.0
 */
@RestController
@RequestMapping("/router")
public class RouterController {
    @Autowired
    RouterService routerService;

    @Autowired
    UserService userService;

    @Autowired
    MANETService manetService;

    /**
     * leave or join
     * @param jsonData
     * @return
     */
    @SneakyThrows
    @ResponseBody
    @PostMapping("/update")
    public AJAXReturn updateRouter(@RequestBody String jsonData) {
        // items type
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        JSONObject map = JSONObject.parseObject(jsonData, JSONObject.class);
        String type = map.get("type").toString();
        HashMap<String, Object> deviceInfo = JsonUtils.jsonToPojo(map.get("own").toString(),HashMap.class);
        String uuid = deviceInfo.get("uuid").toString();
        String MANET_UUID = "";
        String sourceName = deviceInfo.get("username").toString();
        JSONArray jsonArray = map.getJSONArray("items");
        HashMap<String, Object> param = new HashMap<>();
        param.put("uuid",uuid);
        HashMap<String, Object> isExist = manetService.queryMANET_memberByMap(param);
        if(isExist != null){
            MANET_UUID = isExist.get("MANET_UUID").toString();
        }

        if(type.equals("unpaired")){
            // leave get MANET_UUID
            // delete MANET_member reform router
            MANET_UUID = deviceInfo.get("manet_UUID").toString();
            param = new HashMap<>();
            param.put("uuid",MANET_UUID);
            MANETInfo manetInfo = manetService.queryMANET(param);
            if(manetInfo!=null){
                if(!manetInfo.getOwnerID().equals(uuid)){
                    param.put("uuid",uuid);
                    manetService.deleteMANET_member(param);
                }
            }
            param = new HashMap<>();
            param.put("sourceName", sourceName);
            routerService.deleteNeighborGraphBymap(param);
            param.put("source", sourceName);
            routerService.deleteRouterByMap(param);
            param = new HashMap<>();
            param.put("desName", sourceName);
            routerService.deleteNeighborGraphBymap(param);
            param.put("des", sourceName);
            routerService.deleteRouterByMap(param);
        }else {
            // join get MANET_UUID
            JSONArray ja = jsonArray.getJSONArray(0);
            JSONObject jsonObject = ja.getJSONObject(0);
            String step1 = jsonObject.get("name").toString();
            HashMap<String , Object> hashMap =  new HashMap<>();
            hashMap.put("username",step1);
            DeviceInfo tmp = userService.queryUserInfo(hashMap);
            hashMap.put("uuid",tmp.getUuid());
            HashMap<String, Object> MANET_member = manetService.queryMANET_memberByMap(hashMap);
            if(MANET_member != null){
                MANET_UUID =  MANET_member.get("MANET_UUID").toString();
            }else {
                param.put("uuid",tmp.getUuid());
            }
            param.put("MANET_UUID",MANET_UUID);
            MANET_member = manetService.queryMANET_memberByMap(param);
            if(MANET_member == null){
                manetService.insertMANETmember(param);
            }
            for (int i = 0;i< jsonArray.size();i++) {
                ja = jsonArray.getJSONArray(i);
                jsonObject = ja.getJSONObject(0) ;
                String name = jsonObject.getString("name");
                String MAC = jsonObject.getString("MAC");
                param = new HashMap<>();
                param.put("MANET_UUID", MANET_UUID);
                param.put("sourceName", sourceName);
                param.put("desMAC", MAC);
                param.put("desName", name);
                updateGraph(param);
                param.put("sourceName", name);
                param.put("desName", sourceName);
                updateGraph(param);
            }
        }
        //reform router for each nodes
        fromRouter(MANET_UUID);
        return AJAXReturn.success("",MANET_UUID);
    }

    @SneakyThrows
    @ResponseBody
    @GetMapping("/deleteAll")
    public AJAXReturn delete() {
        routerService.deleteRouter();
        routerService.deleteGraph();
        return AJAXReturn.success();
    }

    @SneakyThrows
    @ResponseBody
    @GetMapping("/viewAll")
    public AJAXReturn viewAll() {
        List<NeighborGraph> graphs  = routerService.findAllNeighborGraph(new HashMap<>());
        List<Router> routers  = routerService.findRoutersByMap(new HashMap<>());
        HashMap<String, Object> ans = new HashMap<>();
        ans.put("graphs",graphs);
        ans.put("routers",routers);
        return AJAXReturn.success("",ans);
    }

    @SneakyThrows
    @ResponseBody
    @PostMapping("/get")
    public AJAXReturn getEachRouter(@RequestBody String jsonData) {
        // MANET_UUID
        // source MAC or name
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map = JSONObject.parseObject(jsonData, HashMap.class);
        List<Router> routers = routerService.findRoutersByMap(map);
        if (routers.size() == 0) {
            return AJAXReturn.warn("no data");
        }
        List<NeighborGraph> member = routerService.findAllNeighborGraph(map);
        HashMap<String, Object> res = new HashMap<>();
        res.put("member", JsonUtils.objectToJson(member));
        res.put("router", JsonUtils.objectToJson(routers));
        String ans = JsonUtils.objectToJson(res);
        return AJAXReturn.success("", ans);
    }



    @SneakyThrows
    @ResponseBody
    @PostMapping("/upload")
    public AJAXReturn uploadEachRouter(@RequestBody String jsonData) {
        // source MAC  name
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> map = JSONObject.parseObject(jsonData, HashMap.class);
        String sourceName = map.get("sourceName").toString();
        String sourceMAC = map.get("sourceMAC").toString();
        String MANET_UUID = map.get("MANET_UUID").toString();
        String listStr = map.get("neighbors").toString().replaceAll("\\[","").replaceAll("\\]","");
        listStr = "["+listStr+"]";
        JSONArray jsonArray =  JSONObject.parseArray(listStr);
        HashMap<String, Object>  param = new HashMap<>();
        param.put("sourceName", sourceName);
        String realMID = findMANETUUID(sourceName);
        MANET_UUID = realMID == null ? MANET_UUID : realMID;
//        deleteGraphSameSourceDes(param);
        for(int i =0;i<jsonArray.size();i++){
            JSONObject jb = jsonArray.getJSONObject(i);
            updateGraph(sourceName,sourceMAC,jb.get("neighborName").toString(),jb.get("neighborMac").toString(),MANET_UUID);
            updateGraph(jb.get("neighborName").toString(),jb.get("neighborMac").toString(),sourceName,sourceMAC,MANET_UUID);
        }
        AJAXReturn res =  fromRouter(MANET_UUID);
        String ans = "";
        HashMap<String, String> msg = new HashMap<>();
        if(res.getCode() == 0 ){
            map = new HashMap<>();
            map.put("source",sourceName);
            List<Router> routers = routerService.findRoutersByMap(map);
            if (routers.size() == 0) {
                return AJAXReturn.warn("no data",MANET_UUID);
            }
            List<NeighborGraph> member = routerService.findAllNeighborGraph(map);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("member", JsonUtils.objectToJson(member));
            hashMap.put("router", JsonUtils.objectToJson(routers));
            ans = JsonUtils.objectToJson(hashMap);
            if(sourceName.equals(currentOwner)){
                msg.put("userInfo","owner");
            }else {
                msg.put("userInfo","member");
            }
        }
        msg.put("MANET_UUID",MANET_UUID);
        msg.put("router",ans);
        return AJAXReturn.success("",JsonUtils.objectToJson(msg));
    }
    private void updateGraph( String sourceName, String sourceMAC, String neighborName, String neighborMac, String MANET_UUID) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("timeRecord", new Date());
        param.put("MANET_UUID", MANET_UUID);
        param.put("sourceName", sourceName);
        param.put("sourceMAC", sourceMAC);
        param.put("desMAC", neighborMac);
        param.put("desName", neighborName);

        routerService.deleteNeighborGraphBymap(param);
         routerService.insertNeighborGraph(param);
    }
    private String findMANETUUID(String sourceName) {
        HashMap<String, Object> getCrr = new HashMap<>();
        getCrr.put("name",sourceName);
        return classificationGraph(getCrr);
    }


    @SneakyThrows
    @ResponseBody
    @GetMapping("/graph/test")
    public AJAXReturn graphtest() {
        List<NeighborGraph> graphs = routerService.findAllNeighborGraph(new HashMap<>());
        Graph<String> form = new Graph<String>();
        HashMap<String, String> seen = new HashMap<>();
        for (NeighborGraph graph : graphs) {
            String name = graph.getDesName();
            String name2 = graph.getSourceName();
            if (seen.containsKey(name)){
                if(!seen.get(name).equals(name2)){
                    seen.put(name,name2);
                    form.addEdge(name, name2, true);
                }
            }else {
                seen.put(name,name2);
                form.addEdge(name, name2, true);
            }
        }
        System.out.println("Graph:\n" + form.toString());

        form.hasEdge("A" ,"C");
        form.hasEdge("B" ,"C");
        form.hasEdge("C" ,"D");
        form.hasEdge("C" ,"E");
        form.hasEdge("A" ,"B");
        form.getVertexCount();
        form.hasVertex("C");

        return AJAXReturn.success();

    }


    @SneakyThrows
    @ResponseBody
    @GetMapping("/test")
    public AJAXReturn test() {
        List<NeighborGraph> graphs = routerService.findAllNeighborGraph(new HashMap<>());
        HashMap<String, List<String>> pairedMap = new HashMap<>();
        List<String> set = new ArrayList<>();
        for (NeighborGraph graph : graphs) {
            String source = graph.getSourceName();
            String dest = graph.getDesName();
            if (!set.contains(source)) {
                set.add(source);
            } else if (!set.contains(dest)) {
                set.add(dest);
            }
            if (pairedMap.containsKey(source)) {
                List<String> tmp = pairedMap.get(source);
                if (!tmp.contains(dest)) {
                    tmp.add(dest);
                    pairedMap.put(source, tmp);
                }
            } else {
                List<String> tmp = new ArrayList<>();
                tmp.add(dest);
                pairedMap.put(source, tmp);
            }
        }

        List<HashMap<String, HashSet<String>>> ans = new ArrayList<>();
        int index  =1;
        while (set.size() != 0) {
            Queue<String> queue = new ArrayDeque<>();
            HashSet<String> sameGroup = new HashSet<>();
            queue.add(set.get(0));
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    String tmp_name = queue.poll();
                    sameGroup.add(tmp_name);
                    set.remove(tmp_name);
                    List<String> tmp = pairedMap.get(tmp_name);
                    for (String s : tmp) {
                        if (sameGroup.contains(s)) continue;
                        if (!queue.contains(s)) {
                            queue.add(s);
                        }
                    }
                }
            }
            HashMap map = new HashMap();
            map.put("group",index++);
            map.put("membership",sameGroup);
            ans.add(map);
        }

        return AJAXReturn.success("", ans);
    }



    private String classificationGraph( HashMap<String, Object> getCrr) {
        List<NeighborGraph> graphs = routerService.findAllNeighborGraph(new HashMap<>());
        HashMap<String, List<String>> pairedMap = new HashMap<>();
        List<String> set = new ArrayList<>();
        for (NeighborGraph graph : graphs) {
            String source = graph.getSourceName();
            String dest = graph.getDesName();
            if (!set.contains(source)) {
                set.add(source);
            } else if (!set.contains(dest)) {
                set.add(dest);
            }
            if (pairedMap.containsKey(source)) {
                List<String> tmp = pairedMap.get(source);
                if (!tmp.contains(dest)) {
                    tmp.add(dest);
                    pairedMap.put(source, tmp);
                }
            } else {
                List<String> tmp = new ArrayList<>();
                tmp.add(dest);
                pairedMap.put(source, tmp);
            }
        }

        List<HashSet<String>> ans = new ArrayList<>();
        while (set.size() != 0) {
            Queue<String> queue = new ArrayDeque<>();
            HashSet<String> sameGroup = new HashSet<>();
            queue.add(set.get(0));
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    String tmp_name = queue.poll();
                    sameGroup.add(tmp_name);
                    set.remove(tmp_name);
                    List<String> tmp = pairedMap.get(tmp_name);
                    for (String s : tmp) {
                        if (sameGroup.contains(s)) continue;
                        if (!queue.contains(s)) {
                            queue.add(s);
                        }
                    }
                }
            }
            ans.add(sameGroup);
        }


        Set<String> seen = new HashSet<>();

        for (HashSet<String> tmp : ans) {
            String mid = "";
            for (String s : tmp) {
                boolean exist = false;
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", s);
                List<NeighborGraph> isExistList = routerService.findAllNeighborGraphByMap(map);
                for (NeighborGraph graph : isExistList) {
                    mid = graph.getMANET_UUID() == null ? mid : graph.getMANET_UUID();
                    if (!mid.equals("")) {
                        exist = true;
                        break;
                    }
                }
                if(exist) break;
            }
            if (mid.equals("")) {
                mid = RandomID.getRandomOrderNumber();
            }
            if (mid.equals("none")) {
                mid = RandomID.getRandomOrderNumber();
            }
            if(seen.contains(mid)){
                mid = RandomID.getRandomOrderNumber();
            }
            seen.add(mid);
            for (String s : tmp) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("MANET_UUID", mid);
                map.put("name", s);
                routerService.updateNeighborGraph(map);
                map = new HashMap<>();
                map.put("username", s);
                DeviceInfo info = userService.queryUserInfo(map);
                if (info == null) {
                    HashMap<String, Object> user = new HashMap<>();
                    user.put("username", s);
                    String uuid = String.valueOf(RandomID.genIDWorker());
                    info = new DeviceInfo();
                    info.setUuid(uuid);
                    user.put("uuid", uuid);
                    user.put("password", 1234);
                    user.put("loginDate", new Date());
                    user.put("registerDate", new Date());
                    user.put("status", "1");
                    userService.registerUser(user);
                }
                map = new HashMap<>();
                map.put("uuid", info.getUuid());
                manetService.deleteMANET_member(map);
                map.put("MANET_UUID", mid);
                manetService.insertMANETmember(map);
            }
        }

        List<NeighborGraph> res = routerService.findAllNeighborGraphByMap(getCrr);
        if(res.size() == 0) return null;
        return res.get(0).getMANET_UUID();
    }



    private void deleteGraphSameSourceDes(HashMap<String, Object> param){
        routerService.deleteNeighborGraphSameSoucreDes(param);
    }
    private void updateGraph(HashMap<String, Object> param, int type ) {
        routerService.insertNeighborGraph(param);
    }

    private void updateGraph(HashMap<String, Object> param ) {
        routerService.deleteNeighborGraphBymap(param);
        routerService.insertNeighborGraph(param);
    }

    static String currentOwner = null;
    private void deletRouter(String MANET_UUID){
        routerService.deleteRouterByMid(MANET_UUID);
        HashSet<String> midSet = new HashSet<>();
        List<NeighborGraph> graphs = routerService.findAllNeighborGraph(new HashMap<>());
        for (NeighborGraph d : graphs){
            midSet.add(d.getMANET_UUID());
        }
        for (String s : midSet){
            routerService.deleteRouterExceptMid(s);
        }
    }
    private AJAXReturn fromRouter(String MANET_UUID){
        deletRouter(MANET_UUID);
        HashMap<String, Object> map = new HashMap<>();
        map.put("MANET_UUID",MANET_UUID);
        List<NeighborGraph> graphs = routerService.findAllNeighborGraph(map);
        Graph<String> form = new Graph<String>();
        Set<String> title = new HashSet<>();
        HashMap<String, String> seen = new HashMap<>();
        for (NeighborGraph graph : graphs) {
            String name = graph.getDesName();
            String name2 = graph.getSourceName();
            title.add(name);
            title.add(name2);
            if (seen.containsKey(name)){
                if(!seen.get(name).equals(name2)){
                    seen.put(name,name2);
                    form.addEdge(name, name2, true);
                }
            }else {
                seen.put(name,name2);
                form.addEdge(name, name2, true);
            }
        }

        System.out.println("Graph:\n" + form.toString());
        // findtheowner
        findtheowner(MANET_UUID,form);
        for (String name : title) {
            HashMap<String, Object> path = new HashMap<>();
            path.put("source", name);
            path.put("MANET_UUID",MANET_UUID);
            Graph.TreeNode A = form.getPath(name);
            for (String name2 : title) {
                if (!name2.equals(name)) {
                    path.put("dest", name2);
                    List<String> list = new ArrayList<>();
                    list.add(name);
                    form.TraversalPath(A, list, name2);
                    path.put("hop", form.currentPath.size() - 1 );
                    path.put("path", form.currentPath.toString());
                    reloadRouterDB(path);
                }
            }
        }
        return AJAXReturn.success();
    }

    private void findtheowner(String MANET_UUID, Graph<String> form) {
        int max = 0;
        String maxName = null;
        int number = 0;
        HashSet<String> set = new HashSet<>();
        for (Map.Entry<String, List<String>> entry : form.PathNode.entrySet()) {
            String name = entry.getKey();
            List<String> list = entry.getValue();
            if(list.size() > max){
                max = list.size();
                maxName = name;
            }
            set.addAll(list);
        }
        number = set.size();
        if(max!=0 && maxName != null){
            currentOwner = maxName;
            HashMap<String, Object> query = new HashMap<>();
            query.put("username",maxName);
            DeviceInfo deviceInfo = userService.queryUserInfo(query);
            query = new HashMap<>();
            query.put("ownerID",deviceInfo.getUuid());
            MANETInfo manetInfo = manetService.queryMANET(query);
            if(manetInfo != null){
                manetService.deleteMANET(manetInfo.getOwnerID());
            }else {
                query = new HashMap<>();
                query.put("uuid",MANET_UUID);
                manetInfo = manetService.queryMANET(query);
                query.put("ownerID",deviceInfo.getUuid());
                query.put("number",number);
                if(manetInfo == null){
                    manetService.insertMANET(query);
                }else {
                    manetService.updateMANTE(query);
                }
            }
        }
    }


    private void reloadRouterDB(HashMap<String, Object> map) {
        routerService.insertRouter(map);
    }


}
