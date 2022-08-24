//package com.yang.manet;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//
///**
// * @ClassName:Application
// * @Auther: yyj
// * @Description:
// * @Date: 16/06/2022 19:12
// * @Version: v1.0
// */
///*
//为了不使用web.xml,官方提供这个实现类
//当启动web容器的时候，会自动到添加的相应jar包下找到META-INF/services下以ServletContainerInitializer的全路径名称命名的文件，它的内容为ServletContainerInitializer实现类的全路径，将它们实例化
//*/
//public class Application extends SpringBootServletInitializer{
//
//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(Application.class);
//    }
//}