package com.yang.manet.mapper;

import com.yang.manet.entity.DeviceInfo;
import com.yang.manet.entity.MANETInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName:MANETMapper
 * @Auther: yyj
 * @Description:
 * @Date: 12/06/2022 14:45
 * @Version: v1.0
 */
public interface MANETMapper {
    void insertMANET(HashMap<String, Object> map);

    MANETInfo queryMANET(HashMap<String, Object> map);

    List<DeviceInfo> queryMANET_member(HashMap<String, Object> map);

    List<DeviceInfo> queryMANET_memberOther(HashMap<String, Object> map);

    List<MANETInfo> queryMANETList(HashMap<String, Object> map);

    HashMap<String, Object> queryMANET_memberByMap(HashMap<String, Object> map);

    void insertMANETmember(HashMap<String, Object> map);

    void deleteMANET_member(HashMap<String, Object> map);


    @Select("select * from android.MANET_member")
    List<HashMap<String, Object>> queryMANET_memberTable();

    @Select("select * from android.MANET_member")
    List<MANETInfo> queryAllMANET();

    @Delete("delete from android.MANET where ownerID = #{ownerID}  ")
    void deleteMANET(String ownerID);

    void updateMANTE(HashMap<String, Object> query);
}
