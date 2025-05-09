package com.boxuegu.view;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boxuegu.R;
import com.boxuegu.activity.LoginActivity;
//import com.boxuegu.activity.PlayHistoryActivity;
//import com.boxuegu.activity.SettingActivity;
//import com.boxuegu.activity.UserInfoActivity;
import com.boxuegu.activity.PlayHistoryActivity;
import com.boxuegu.activity.SettingActivity;
import com.boxuegu.activity.UserInfoActivity;
import com.boxuegu.utils.UtilsHelper;

/*
* 我的界面 步骤一：创建MyInfoView类
* */
public class MyInfoView implements View.OnClickListener {

//    声明全局对象
    public ImageView iv_head_icon;
    private LinearLayout ll_head;
    private RelativeLayout rl_course_history, rl_setting;
    private TextView tv_user_name;
    private Activity mContext;
    private LayoutInflater mInflater;
    private View mCurrentView;
    private boolean isLogin = false; //记录登录状态

//    构造方法  里面调用from方法 获取 LayoutInflater类对象，以便于后续加载布局文件时使用
    public MyInfoView(Activity context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    /*
    * 步骤3：初始化界面控件
    * */
    private void initView() {
        mCurrentView = mInflater.inflate(R.layout.main_view_myinfo, null);//获取 我的布局文件 对象（转化）
        ll_head = mCurrentView.findViewById(R.id.ll_head); //该对象 表示（包含）用户头像和用户名控件所在的布局
        iv_head_icon = mCurrentView.findViewById(R.id.iv_head_icon);
        rl_course_history = mCurrentView.findViewById(R.id.rl_course_history);
        rl_setting = mCurrentView.findViewById(R.id.rl_setting);
        tv_user_name = mCurrentView.findViewById(R.id.tv_user_name);
        mCurrentView.setVisibility(View.VISIBLE);
        //调用setLoginParams方法，用于设置登录时界面控件的显示信息
        setLoginParams(isLogin);
        ll_head.setOnClickListener(this);
        rl_course_history.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
    }


    /**
     * 设置"我"的界面中用户名控件的显示信息
     */
    public void setLoginParams(boolean isLogin) {
        if (isLogin) {
//            如果用户登录了，获取登录用户的用户名信息，并且把用户名  设置到用户名 控件上
            tv_user_name.setText(UtilsHelper.readLoginUserName(mContext));
        } else {
//            没有登录，直接设置 文本到 用户名 控件上
            tv_user_name.setText("点击登录");
        }
    }

    /*
    步骤4：获取界面控件的点击事件
    * 实现界面控件的点击事件，注意不要忘记实现 View.OnClickListener接口
    * */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head:  //表示用户头像和用户名控件所在的布局，用户点击头像或者用户名
                if (UtilsHelper.readLoginStatus(mContext)) {
                    //如果用户已登录，则跳转到个人资料界面
                    Intent intent = new Intent(mContext, UserInfoActivity.class);
                    mContext.startActivity(intent);
                } else {
                    //否则跳转到登录界面
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivityForResult(intent, 1);
                }
                break;
            case R.id.rl_course_history://用户点击播放 记录
                if (UtilsHelper.readLoginStatus(mContext)) {
                    //已登录，则跳转到播放记录界面
                    Intent intent=new Intent(mContext, PlayHistoryActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "您还未登录，请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_setting://用户点击设置
                if (UtilsHelper.readLoginStatus(mContext)) {
                    //跳转到设置界面
                    Intent intent=new Intent(mContext, SettingActivity.class);
                    mContext.startActivityForResult(intent,1);
                } else {
                    Toast.makeText(mContext, "您还未登录，请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    /*
    * 步骤5：获取“我”的界面，即初始化界面视图
    * */
    public View getView() {
        //调用readLoginStatus方法来获取用户登录状态
        isLogin = UtilsHelper.readLoginStatus(mContext);
//        我的界面布局文件视图为空
        if (mCurrentView == null) {
//            在 initView() initView()中需要使用登录状态来显示 用户名控件上需要显示 的 用户名信息
//            即在 initView()中调用 setLoginParams（）来置"我"的界面中用户名控件的显示信息
            initView();//初始化界面控件
        }
//        不为空
        return mCurrentView;
    }
    /*
    * 步骤5：显示“我”的界面，可见即显示 出来
    * 接下来还需要将我的界面设置（加载）到底部导航栏的 界面中
    * */
    public void showView() {
        //        我的界面布局文件视图为空
        if (mCurrentView == null) {
            initView();//初始化界面控件
        }
        //        不为空
        mCurrentView.setVisibility(View.VISIBLE);//设置"我"的界面为显示状态
    }



}
