package com.boxuegu.utils;

import com.boxuegu.bean.BannerBean;
import com.boxuegu.bean.CourseBean;
import com.boxuegu.bean.ExercisesBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/*
*  由于从Tomcat服务器上获取的习题数据是JSON类型的，不能直接显示到界面上，
*  所以需要创建 JsonParse类，该类用于解析从服务器上获取的JSON数据
* */
public class JsonParse {

    private static JsonParse instance;
//  创建无参构造方法   用来 实例化该 类 对象
    private JsonParse() {
    }

//  使用 单例模式  用来 创建该 类 对象
    public static JsonParse getInstance() {
        if (instance == null) {
            instance = new JsonParse();
        }
        return instance;
    }

    /*
    * getExercisesList方法 用来 解析获取的json数据
    * */
    public List<ExercisesBean> getExercisesList(String json) {
        // 创建 gson 对象
        Gson gson = new Gson();
        // 创建一个TypeToken的匿名子类对象，并调用该对象的getType()方法
        Type listType = new TypeToken<List<ExercisesBean>>() {}.getType();
        // 第一个参数是要进行解析的就json数据,解析出来存放到listType变量中，最后存储到 List<BannerBean>集合中
        // 即：把获取到的数据存放在集合 exercisesList 中
        List<ExercisesBean> exercisesList = gson.fromJson(json, listType);
//        返回集合对象（该对象中 的数据都是把json数据解析完之后）
        return exercisesList;
    }

    /*
    *
    * */
    public List<BannerBean> getBannerList(String json) {
//        创建 gson 对象
        Gson gson = new Gson();
        // 创建一个TypeToken的匿名子类对象，并调用该对象的getType()方法
        Type listType = new TypeToken<List<BannerBean>>() {}.getType();
        // 第一个参数是要进行解析的就json数据,解析出来存放到listType变量中，最后存储到 List<BannerBean>集合中
        // 即：把获取到的数据存放在集合bannerList中
        List<BannerBean> bannerList = gson.fromJson(json, listType);
//        返回集合对象（该对象中 的数据都是把json数据解析完之后）
        return bannerList;
    }

    public List<CourseBean> getCourseList(String json) {
        Gson gson = new Gson();
        // 创建一个TypeToken的匿名子类对象，并调用该对象的getType()方法
        Type listType = new TypeToken<List<CourseBean>>() {}.getType();
        // 把获取到的数据存放在集合courseList中
        List<CourseBean> courseList = gson.fromJson(json, listType);
        return courseList;
    }

}
