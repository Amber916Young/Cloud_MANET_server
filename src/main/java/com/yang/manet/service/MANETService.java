package com.yang.manet.service;

import com.yang.manet.entity.DeviceInfo;
import com.yang.manet.entity.MANETInfo;
import com.yang.manet.mapper.MANETMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName:MANETService
 * @Auther: yyj
 * @Description:
 * @Date: 12/06/2022 14:45
 * @Version: v1.0
 */
@Service
public class MANETService {
    @Autowired
    MANETMapper mapper;

    public void insertMANET(HashMap<String, Object> map) {
        mapper.insertMANET(map);
    }

    public MANETInfo queryMANET(HashMap<String, Object> map) {
        return mapper.queryMANET(map);
    }

    public List<DeviceInfo> queryMANET_member(HashMap<String, Object> map) {
        return mapper.queryMANET_member(map);
    }

    public List<DeviceInfo> queryMANET_memberOther(HashMap<String, Object> map) {
        return mapper.queryMANET_memberOther(map);
    }

    public List<MANETInfo> queryMANETList(HashMap<String, Object> map) {
        return mapper.queryMANETList(map);

    }

    public HashMap<String, Object> queryMANET_memberByMap(HashMap<String, Object> map) {
        return mapper.queryMANET_memberByMap(map);
    }

    public void insertMANETmember(HashMap<String, Object> map) {
        mapper.insertMANETmember(map);
    }

    public void deleteMANET_member(HashMap<String, Object> map) {
        mapper.deleteMANET_member(map);
    }

    public List<HashMap<String, Object>> queryMANET_memberTable() {
        return mapper.queryMANET_memberTable();
    }


    public List<MANETInfo> queryAllMANET() {
        return mapper.queryAllMANET();

    }


    public void deleteMANET(String ownerID) {
         mapper.deleteMANET(ownerID);
    }

    public void updateMANTE(HashMap<String, Object> query) {
        mapper.updateMANTE(query);
    }
}
