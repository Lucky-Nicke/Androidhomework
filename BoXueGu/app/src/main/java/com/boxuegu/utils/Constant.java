package com.boxuegu.utils;

/*
* 由于博学谷项目中的数据需要通过网络请求从Tomcat（一个小型服务器）上获取，
* 所以在博学谷程序中需要创建一个Constant类，在该类中存放各界面请求数据时使用的接口地址。
* 注意：（1）内网接口为本机ip地址  (2)IP地址中间不要带有空格
* */
public class Constant {
    //内网接口
//    public static final String WEB_SITE = "http://172.16.43.20:8080/boxuegu";
    public static final String WEB_SITE = "http://192.168.31.216:8080/boxuegu";
    //获取习题列表数据的请求地址
    public static final String REQUEST_EXERCISES_URL = "/exercises_list_data.json";
    //获取广告栏数据的部分请求地址
    public static final String REQUEST_BANNER_URL = "/banner_list_data.json";
    //获取课程数据的部分请求地址
    public static final String REQUEST_COURSE_URL = "/course_list_data.json";
}
