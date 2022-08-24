package com.yang.manet.entity;

import lombok.Data;

/**
 * @ClassName:MANET
 * @Auther: yyj
 * @Description:
 * @Date: 12/06/2022 14:53
 * @Version: v1.0
 */
@Data
public class MANETInfo {
    private String uuid;
    private String ownerID;  // device uuid
    private int number;
}
