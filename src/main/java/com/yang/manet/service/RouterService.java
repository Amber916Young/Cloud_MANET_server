package com.yang.manet.service;

import com.yang.manet.entity.NeighborGraph;
import com.yang.manet.entity.Router;
import com.yang.manet.mapper.MessageMapper;
import com.yang.manet.mapper.RouterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName:RouterService
 * @Auther: yyj
 * @Description:
 * @Date: 24/06/2022 14:41
 * @Version: v1.0
 */
@Service
public class RouterService {
    @Autowired
    RouterMapper routerMapper;



    public Router findRouterByMap(HashMap<String, Object> map) {
        return routerMapper.findRouterByMap( map);
    }
    public List<Router> findRoutersByMap(HashMap<String, Object> map) {
        return routerMapper.findRoutersByMap( map);
    }
    public void deleteRouterByid(int id) {
        routerMapper.deleteRouterByid( id);
    }

    public void insertRouter(HashMap<String, Object> map) {
        routerMapper.insertRouter( map);
    }

    public NeighborGraph findNeighborGraph(HashMap<String, Object> map) {
        return routerMapper.findNeighborGraph( map);
    }

    public void deleteNeighborGraphByid(int id) {
        routerMapper.deleteNeighborGraphByid( id);
    }

    public void insertNeighborGraph(HashMap<String, Object> map) {
         routerMapper.insertNeighborGraph( map);
    }

    public void deleteNeighborGraphBymap(HashMap<String, Object> param) {
        routerMapper.deleteNeighborGraphBymap( param);
    }

    public void deleteGraph() {
        routerMapper.deleteGraph();
    }

    public void deleteRouter() {
        routerMapper.deleteRouter();
    }

    public void deleteRouterByMap(HashMap<String, Object> map) {
        routerMapper.deleteRouterByMap(map);
    }

    public void deleteNeighborGraphSameSoucreDes(HashMap<String, Object> map) {
        routerMapper.deleteNeighborGraphSameSoucreDes(map);
    }

    public void deleteRouterSameSoucreDes(HashMap<String, Object> map) {
        routerMapper.deleteRouterSameSoucreDes(map);
    }

    public void updateNeighborGraph(HashMap<String, Object> map) {
        routerMapper.updateNeighborGraph(map);

    }
    public List<NeighborGraph> findAllNeighborGraph(HashMap<String ,Object> map) {
        return routerMapper.findAllNeighborGraph( map);
    }
    public List<NeighborGraph> findAllNeighborGraphByMap( HashMap<String, Object> map) {
        return routerMapper.findAllNeighborGraphByMap(map);

    }

    public void updateNeighborGraphById(HashMap<String, Object> map) {
         routerMapper.updateNeighborGraphById(map);
    }

    public void deleteRouterByMid(String manet_uuid) {
        routerMapper.deleteRouterByMid(manet_uuid);
    }

    public void deleteRouterExceptMid(String s) {
        routerMapper.deleteRouterExceptMid(s);
    }
}
