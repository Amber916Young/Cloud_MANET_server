package com.yang.manet.entity;

import lombok.Data;

/**
 * @ClassName:Router
 * @Auther: yyj
 * @Description:
 * @Date: 24/06/2022 16:28
 * @Version: v1.0
 */

@Data
public class Router {
    private int id;
    private String source;
    private String dest;
    private String path;
    private String hop;
}
