package com.boxuegu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boxuegu.activity.LoginActivity;
import com.boxuegu.activity.RegisterActivity;
import com.boxuegu.activity.SplashActivity;
import com.boxuegu.utils.UtilsHelper;
import com.boxuegu.view.CourseView;
import com.boxuegu.view.ExercisesView;
import com.boxuegu.view.MyInfoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout mBodyLayout;   //中间内容栏  mBodyLayout就是底部导航栏2中间部分的 视图
    public LinearLayout mBottomLayout; //底部按钮栏
    private View mCourseBtn,mExercisesBtn,mMyInfoBtn;
    private TextView tv_course,tv_exercises,tv_myInfo;
    private ImageView iv_course,iv_exercises,iv_myInfo;
    private TextView tv_back,tv_main_title;
    private RelativeLayout rl_title_bar;
//    这是后续 需要创建 的三个类，先注释
    private MyInfoView mMyInfoView;
    private ExercisesView mExercisesView;
    private CourseView mCourseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
//        startActivity(intent);

//        测试登录功能，登录界面可以跳到注册界面
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);

        init();
        setListener();         //设置底部导航栏 按钮的点击事件的监听器
        selectDisplayView(0);//默认情况下，显示的是课程界面

    }

    /**
     * 获取界面上的控件
     */
    private void init() {
//        返回按钮
        tv_back = findViewById(R.id.tv_back);
        tv_back.setVisibility(View.GONE); //设置为 隐藏状态  该页面不需要 返回按钮
//        标题栏组件
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("博学谷课程");
//        获取标题栏布局
        rl_title_bar = findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));//蓝色
//        底部导航栏
        mBodyLayout = findViewById(R.id.main_body);
        mBottomLayout = findViewById(R.id.main_bottom_bar);
        mCourseBtn = findViewById(R.id.bottom_bar_course_btn);
        mExercisesBtn = findViewById(R.id.bottom_bar_exercises_btn);
        mMyInfoBtn = findViewById(R.id.bottom_bar_myinfo_btn);
        tv_course = findViewById(R.id.bottom_bar_text_course);
        tv_exercises = findViewById(R.id.bottom_bar_text_exercises);
        tv_myInfo = findViewById(R.id.bottom_bar_text_myinfo);
        iv_course = findViewById(R.id.bottom_bar_image_course);
        iv_exercises = findViewById(R.id.bottom_bar_image_exercises);
        iv_myInfo = findViewById(R.id.bottom_bar_image_myinfo);
    }

    /**
     * 步骤2：设置底部按钮被选中与未被选中的状态
     * 设置底部按钮未被选中时的状态，图片和文本都是灰色
     */
    private void setNotSelectedStatus() {
//        设置文本颜色为灰色
        tv_course.setTextColor(Color.parseColor("#666666"));
        tv_exercises.setTextColor(Color.parseColor("#666666"));
        tv_myInfo.setTextColor(Color.parseColor("#666666"));
//        设置图片颜色为灰色
        iv_course.setImageResource(R.drawable.main_course_icon);
        iv_exercises.setImageResource(R.drawable.main_exercises_icon);
        iv_myInfo.setImageResource(R.drawable.main_my_icon);
//        设置底部导航栏三个按钮 都是未被选中状态  false
        for (int i = 0; i < mBottomLayout.getChildCount(); i++) {
            mBottomLayout.getChildAt(i).setSelected(false);
        }
    }
    /**
     * 步骤2：设置底部按钮被选中与未被选中的状态
     * 设置底部按钮被选中时的状态，图片和文本都是蓝色
     * index为底部按钮 的索引值，即下标
     */
    private void setSelectedStatus(int index) {
        switch (index) {
            case 0: //0表示课程按钮
//                课程按钮 被选中  状态true
                mCourseBtn.setSelected(true);
                iv_course.setImageResource(R.drawable.main_course_icon_selected); //设置被选中的图片 为蓝色
                tv_course.setTextColor(Color.parseColor("#0097F7"));//设置被选中的文本颜色 蓝色
                rl_title_bar.setVisibility(View.VISIBLE);//课程界面 需要显示标题栏，即 设置 标题栏 为可见状态
                tv_main_title.setText("博学谷课程");//设置标题栏 中的标题文本 为  “博学谷课程”
                break;
            case 1: //习题按钮
                mExercisesBtn.setSelected(true);
                iv_exercises.setImageResource(R.drawable.main_exercises_icon_selected);
                tv_exercises.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("博学谷习题");
                break;
            case 2: //“我”按钮
                mMyInfoBtn.setSelected(true);
                iv_myInfo.setImageResource(R.drawable.main_my_icon_selected);
                tv_myInfo.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 步骤3：创建和隐藏界面的中间部分视图
     * 还要设置 底部导航栏界面的中间部分视图 为隐藏还是创建（显示）
     * 隐藏底部导航栏界面的中间部分视图
     */
    private void hideAllView() {
        for (int i = 0; i < mBodyLayout.getChildCount(); i++) {
            mBodyLayout.getChildAt(i).setVisibility(View.GONE);
        }
    }
    /**
     * 步骤3：创建和隐藏界面的中间部分视图
     * 创建视图
     */
    private void createView(int viewIndex) {
        switch (viewIndex) {
            case 0:
                //0为课程界面
//                以下界面还没创建，先注释掉
                if (mCourseView == null) {
                    mCourseView = new CourseView(this);
                    mBodyLayout.addView(mCourseView.getView());
                } else {
                    mCourseView.getView();
                }
                mCourseView.showView();
                break;
            case 1:
                //习题界面
                if (mExercisesView == null) {
                    mExercisesView = new ExercisesView(this);        //为空 则需要创建实例化ExercisesView类
                    mBodyLayout.addView(mExercisesView.getView());  //将习题界面添加到底部导航栏的布局中
                } else {
                    mExercisesView.getView(); //获取习题界面
                }
                mExercisesView.showView();    //显示习题界面
                break;
            case 2:
//                "我"的界面
                if (mMyInfoView == null) {//如果 MyInfoView类对象为空
//                    先创建类对象
                    mMyInfoView = new MyInfoView(this);
                    //加载“我”的界面显示到底部导航栏的中间部分中
                    mBodyLayout.addView(mMyInfoView.getView());
                } else {
                    mMyInfoView.getView();//不为空，则获取“我”的界面
                }
                mMyInfoView.showView();  //最后调用showView（）方法，显示“我”的界面到底部导航栏的界面 中
                break;
        }
    }


    /**
     * 步骤4：设置底部被选中按钮对应的界面
     * 设置底部按钮被选中时，对应的界面，那中间部分的视图
     * index的值有三个  012，分别表示0为课程界面，1习题界面，2我的界面
     */
    private void selectDisplayView(int index) {
        //隐藏所有视图，先隐藏三个按钮对应的视图
        hideAllView();
        //创建被选中按钮对应的视图
        createView(index);
        //设置被选中按钮的选中状态，即设置被选中按钮的 “中间部分” 的 显示
        setSelectedStatus(index);
    }


    /**
     * 设置底部3个按钮的点击事件的监听器
     */
    private void setListener() {
        for (int i = 0; i < mBottomLayout.getChildCount(); i++) {
            mBottomLayout.getChildAt(i).setOnClickListener(this);
        }
    }
    /*
    步骤5：实现界面控件的点击事件功能
    * implements View.OnClickListener  自身类实现父接口，判断用户点击 的是哪一个按钮，课程、习题、我的界面
    * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //"课程"按钮的点击事件，被选中 的话
            case R.id.bottom_bar_course_btn:
//                先设置三个按钮都是未选中状态
                setNotSelectedStatus();
//                然后调用selectDisplayView方法把课程按钮对应的视图部分设置到底部导航栏的中间位置
                selectDisplayView(0);
                break;
            //"习题"按钮的点击事件
            case R.id.bottom_bar_exercises_btn:
                setNotSelectedStatus();
                selectDisplayView(1);
                break;
            //"我"的按钮的点击事件
            case R.id.bottom_bar_myinfo_btn:
                setNotSelectedStatus();
                selectDisplayView(2);
                break;
            default:
                break;
        }
    }


    /*
    * 步骤8：实现退出博学谷程序的功能
    * 如果两次点击的时间间隔小于2秒，则程序会直接退出博学谷程序
    *  为了实现点击设备上的返回键退出博学谷程序的功能，我们需要在MainActivity中重写onKeyDown()方法，
    *  当用户点击返回键时，程序会调用onKeyDown()方法实现退出博学谷程序的功能
    * */
    protected long exitTime;//记录第一次点击时的时间
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        判断keyCode的值是否等于KEYCODE_BACK，值KEYCODE_BACK表示 当前用户点击的是设备上的返回键
//        接着判断getAction()的返回值是否为ACTION_DOWN，表示的是按键 被按下的事件
//        两个都成立的话，表示按钮 正处于被按下的状态，在这时候就需要处理返回键被按下的 逻辑
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            如果  当前时间 减去 第一次点击设备上的返回键的时间 大于 2秒
//            这是两次点击返回键大于两秒的情况
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出博学谷", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis(); //记录当前点击返回键的时间
            } else {
//                两次点击返回键小于两秒的情况
//                关闭底部导航栏界面
                MainActivity.this.finish();
//                判断是否处于登录状态
                if (UtilsHelper.readLoginStatus(MainActivity.this)) {
                    //实现用户退出功能，即清除登录状态与用户名
                    UtilsHelper.clearLoginStatus(MainActivity.this);
                }
//                退出程序
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /*
    *  显示“我”的界面到 底部导航栏的界面中之后，还需要显示一下  我的界面  中的  用户名信息  也显示出来
    * 步骤7：显示用户名到“我”的界面上
    * 接收 登录界面回传过来登录信息，从而设置“我”的界面中用户名控件上的文本信息
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //获取从设置界面或登录界面传递过来的登录状态
            //根据  LoginActivity.java 文件中，用户登录成功 data.putExtra("isLogin", true);把登录状态true传过来
            boolean isLogin=data.getBooleanExtra("isLogin",false); //登录状态 默认false
            if(isLogin){//登录成功时，默认显示的是课程界面
                setNotSelectedStatus();
                selectDisplayView(0);
            }
            if (mMyInfoView != null) {
                //登录成功或退出登录时，根据isLogin的值设置"我"的界面
                // 为true，调用setLoginParams方法 来显示用户名信息，false显示 点击登录 的文本信息
                mMyInfoView.setLoginParams(isLogin);
            }
        }
    }

}
