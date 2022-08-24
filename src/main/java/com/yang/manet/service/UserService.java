package com.yang.manet.service;

import com.yang.manet.entity.DeviceInfo;
import com.yang.manet.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName:UserService
 * @Auther: yyj
 * @Description:
 * @Date: 11/06/2022 18:24
 * @Version: v1.0
 */
@Service
public class UserService {
    @Autowired
    UserMapper mapper;

    public DeviceInfo queryUserInfo(HashMap<String, Object> map) {
        return mapper.queryUserInfo(map);
    }

    public void registerUser(HashMap<String, Object> map) {
        mapper.registerUser(map);
    }

    public List<HashMap> queryAllUsers() {
        return mapper.queryAllUsers();
    }

    public void updateUserInfo(DeviceInfo deviceInfo) {
        mapper.updateUserInfo(deviceInfo);
    }
}
