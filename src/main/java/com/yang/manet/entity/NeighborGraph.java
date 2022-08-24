package com.yang.manet.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName:NeighborGraph
 * @Auther: yyj
 * @Description:
 * @Date: 24/06/2022 14:47
 * @Version: v1.0
 */
@Data
public class NeighborGraph {
    private int id;
    private String sourceName;
    private String sourceMAC;
    private String desName;
    private String desMAC;
    private String MANET_UUID;
    private Date timeRecord;
}
