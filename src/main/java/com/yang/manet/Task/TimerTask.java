package com.yang.manet.Task;

import com.yang.manet.Utils.AJAXReturn;
import com.yang.manet.Utils.RandomID;
import com.yang.manet.entity.DeviceInfo;
import com.yang.manet.entity.NeighborGraph;
import com.yang.manet.service.MANETService;
import com.yang.manet.service.RouterService;
import com.yang.manet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @ClassName:TimerTask
 * @Auther: yyj
 * @Description:
 * @Date: 02/07/2022 19:02
 * @Version: v1.0
 */
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
//@EnableAsync        // 2.开启多线程
public class TimerTask {
    @Autowired
    RouterService routerService;
    @Autowired
    UserService userService;
    @Autowired
    MANETService manetService;

    public void classificationGraph() {
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
    }

    @Async
    @Scheduled(fixedRate= 600000)
    public void configureTasks() {
        System.out.println("执行");
        classificationGraph ();
    }

    @Async
    @Scheduled(fixedRate= 60000)
    public void CheckUpdateGraph() {
        System.out.println("check");
        List<NeighborGraph> graphs = routerService.findAllNeighborGraph(new HashMap<>());
        long now = new Date().getTime();
        for(NeighborGraph graph : graphs){
            if(graph.getTimeRecord() == null){
                routerService.deleteNeighborGraphByid(graph.getId());
            }else {
                long old = graph.getTimeRecord().getTime();
                long sec = (now - old)/1000;
                if(sec > 60){
                    routerService.deleteNeighborGraphByid(graph.getId());
                }
            }
        }
    }
}