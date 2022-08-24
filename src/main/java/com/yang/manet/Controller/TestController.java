package com.yang.manet.Controller;

import com.alibaba.fastjson.JSONObject;
import com.yang.manet.Utils.AJAXReturn;
import com.yang.manet.Utils.RandomID;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;

import java.util.*;



/**
 * @ClassName:TestController
 * @Auther: yyj
 * @Description:
 * @Date: 16/06/2022 12:31
 * @Version: v1.0
 */
@RequestMapping("/")
@Controller
public class TestController {

    @GetMapping("")
    public Object createMANET() {
        return "test";
    }

}
