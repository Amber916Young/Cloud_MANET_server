package com.yang.manet.mapper;

import com.yang.manet.entity.NeighborGraph;
import com.yang.manet.entity.Router;
import org.apache.ibatis.annotations.Delete;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName:RouterMapper
 * @Auther: yyj
 * @Description:
 * @Date: 24/06/2022 14:41
 * @Version: v1.0
 */
public interface RouterMapper {
    List<NeighborGraph> findAllNeighborGraph(HashMap<String ,Object> map);

    Router findRouterByMap(HashMap<String, Object> map);
    List<Router> findRoutersByMap(HashMap<String, Object> map);

    void deleteRouterByid(int id);

    void insertRouter(HashMap<String, Object> map);

    NeighborGraph findNeighborGraph(HashMap<String, Object> map);

    @Delete("delete from android.neighborGraph where id = #{id}")
    void deleteNeighborGraphByid(int id);

    void insertNeighborGraph(HashMap<String, Object> map);

    void deleteNeighborGraphBymap(HashMap<String, Object> param);

    @Delete("delete from  android.neighborGraph ")
    void deleteGraph();

    @Delete("delete from  android.Router ")
    void deleteRouter();

    void deleteRouterByMap(HashMap<String, Object> map);

    void deleteNeighborGraphSameSoucreDes(HashMap<String, Object> map);

    void deleteRouterSameSoucreDes(HashMap<String, Object> map);

    void updateNeighborGraph(HashMap<String, Object> map);

    List<NeighborGraph> findAllNeighborGraphByMap(HashMap<String, Object> map);

    void updateNeighborGraphById(HashMap<String, Object> map);


    @Delete("delete from  android.Router  where MANET_UUID = #{manet_uuid} ")
    void deleteRouterByMid(String manet_uuid);

    @Delete("delete from  android.Router  where MANET_UUID != #{manet_uuid} ")
    void deleteRouterExceptMid(String s);
}
