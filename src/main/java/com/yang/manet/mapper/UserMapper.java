package com.yang.manet.mapper;

import com.yang.manet.entity.DeviceInfo;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName:UserMapper
 * @Auther: yyj
 * @Description:
 * @Date: 11/06/2022 18:25
 * @Version: v1.0
 */
public interface UserMapper {
    DeviceInfo queryUserInfo(HashMap<String, Object> map);

    void registerUser(HashMap<String, Object> map);

    @Select("select * from  android.deviceInfo")
    List<HashMap> queryAllUsers();

    void updateUserInfo(DeviceInfo deviceInfo);
}
