package com.yang.manet.entity;

import lombok.Data;

/**
 * @ClassName:Message
 * @Auther: yyj
 * @Description:
 * @Date: 01/06/2022 22:39
 * @Version: v1.0
 */
@Data
public class Message {
    private String  uuid;
    private String content;
    private String message;
    private String sendDate;
    private String readDate;
    private int isRead; // 0 no 1 already
    private int isUpload; // 0 no 1 already
    private int dataType;
    private String targetName;
    private String targetMAC;
    private String sourceName;
    private String sourceMAC;
    private String uploadName;
    private String uploadMAC;
    private String uploadTime;
    private Boolean saved;
    private Object fileContent;

    public Message() {
    }


    public Message(String uuid, String content, String sendDate, String readDate, int isRead, int isUpload, String targetName, String targetMAC, String sourceName, String sourceMAC, String uploadName, String uploadMAC, String uploadTime) {
        this.uuid = uuid;
        this.content = content;
        this.sendDate = sendDate;
        this.readDate = readDate;
        this.isRead = isRead;
        this.isUpload = isUpload;
        this.targetName = targetName;
        this.targetMAC = targetMAC;
        this.sourceName = sourceName;
        this.sourceMAC = sourceMAC;
        this.uploadName = uploadName;
        this.uploadMAC = uploadMAC;
        this.uploadTime = uploadTime;
    }
}
