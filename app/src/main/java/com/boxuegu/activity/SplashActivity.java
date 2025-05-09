package com.boxuegu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.boxuegu.MainActivity;
import com.boxuegu.R;

import java.util.Timer;
import java.util.TimerTask;
/*
* 欢迎界面（启动页）
* */
public class SplashActivity extends AppCompatActivity {

    private TextView tv_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

//    private void init(){
//
//        //获取显示版本号信息的控件tv_version
//        tv_version=findViewById(R.id.tv_version);
//        try {
//            //获取程序包信息
//            PackageInfo info= getPackageManager().getPackageInfo
//                    (getPackageName(), 0);
//            //将程序版本号信息设置到界面控件上
//            tv_version.setText("V"+info.versionName);
//        }catch (PackageManager.NameNotFoundException e){
//            e.printStackTrace();
//            tv_version.setText("V");
//        }
//        //创建Timer类的对象   Timer类是jdk中 提供的一个定时器工具
//        Timer timer = new Timer();
//        //通过TimerTask类实现界面跳转的功能  TimerTask类是一个实现了Runnable接口 的 抽象类
//        //TimerTask类代表的是 一个可以被  Timer执行的一个任务（task），这个任务是在线程中实现的，即run( )的方法体
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(intent);
//                SplashActivity.this.finish();
//            }
//        };
//        //设置程序延迟3秒之后自动执行任务task
//        timer.schedule(task, 3000);
//    }


    private void init()  {
        tv_version=findViewById(R.id.tv_version);
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = packageInfo.versionName;
        tv_version.setText(versionName);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        timer.schedule(timerTask,3000);
    }



}
