package com.yang.manet.entity;

import lombok.Data;

/**
 * @ClassName:DeviceInfo
 * @Auther: yyj
 * @Description:
 * @Date: 11/06/2022 19:29
 * @Version: v1.0
 */
@Data
public class DeviceInfo {
    private String uuid;
    private String username;
    private String password;
    private String MAC = "none";
    private String loginDate;
    private String registerDate;
    private String status;
    private String MANET_UUID = "none";
    private String role;
}
