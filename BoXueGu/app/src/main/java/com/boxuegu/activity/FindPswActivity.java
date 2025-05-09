package com.boxuegu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.boxuegu.R;
import com.boxuegu.utils.UtilsHelper;

/*
* 找回密码
* */
public class FindPswActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_validate_name, et_user_name;
    private Button btn_validate;
    private TextView tv_main_title;
    private TextView tv_back;
    private String from;
    private TextView tv_reset_psw, tv_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);

        //获取从登录界面和设置界面传递过来的数据
//        设置key的值为from，from是设置密保界面，否则是找回密码界面
        from = getIntent().getStringExtra("from");
        init();
    }

    /*
    * 初始化组件对象
    * */
    private void init() {
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_back = findViewById(R.id.tv_back);
        et_validate_name = findViewById(R.id.et_validate_name);//设置密保的  用户的姓名  注意不是用户账号
        btn_validate = findViewById(R.id.btn_validate);//保存 或者 验证的  按钮
        tv_reset_psw = findViewById(R.id.tv_reset_psw);//初始密码
        et_user_name = findViewById(R.id.et_user_name);//找回密码界面的  用户名可编辑组件
        tv_user_name = findViewById(R.id.tv_user_name);//找回密码界面的  用户名显示文本组件
//        判断  如果key的值是security，则需要 设置密保界面的 信息
        if ("security".equals(from)) {
            tv_main_title.setText("设置密保");
            btn_validate.setText("保存");
        } else {
            tv_main_title.setText("找回密码");
            tv_user_name.setVisibility(View.VISIBLE);
            et_user_name.setVisibility(View.VISIBLE);
            btn_validate.setText("验证");
        }
//        设置监听事件，注册单击事件监听器，自身类实现父接口
        tv_back.setOnClickListener(this);
        btn_validate.setOnClickListener(this);
    }


    /*
    * 保存密保信息
    * 设置密保界面需要将界面中输入的姓名信息作为密保保存到SharedPreferences文件中
    * */
    //validateName为用户输入 姓名
    private void saveSecurity(String validateName) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = sp.edit();
        //获取登录用户名
        String userName= UtilsHelper.readLoginUserName(this);
        //保存密保信息  可以为用户名（userName）+ _security   如    admin_security,  value值为validateName，即 姓名
        editor.putString(userName+ "_security",validateName);
        //提交修改
        editor.commit();
    }
    /*
    * 根据用户名  从loginInfo.xml文件中    获取密保信息
    * 由于找回密码界面需要获取SharedPreferences文件中保存的密保与界面中输入的密保信息进行对比
    * */
    private String readSecurity(String userName) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
//      获取密保信息 默认为空字符串  将获取的密保信息保存到空字符串中
        String security = sp.getString(userName + "_security", "");
//        返回密保信息
        return security;
    }


    /*
    * 实现界面控件的点击事件
    * 设置密保界面与找回密码界面中的“返回”按钮、“保存”或“验证”按钮都需要实现点击功能
    * */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                FindPswActivity.this.finish();
                break;
            case R.id.btn_validate: // btn_validate值为  “保存”或“验证”
//                获取用户输入的 姓名，即密保信息
                String validateName = et_validate_name.getText().toString().trim();
//                判断 key即from的值是否为 security
                if ("security".equals(from)) {//设置密保界面
                    if (TextUtils.isEmpty(validateName)) {
                        Toast.makeText(FindPswActivity.this, "请输入您的姓名", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(FindPswActivity.this, "密保设置成功", Toast.LENGTH_SHORT).show();
                        //保存密保到SharedPreferences文件中
                        saveSecurity(validateName);
                        FindPswActivity.this.finish();
                    }
                } else {//找回密码界面
                    String userName = et_user_name.getText().toString().trim();
//                    根据用户名 从loginInfo.xml文件中    获取密保信息
                    String sp_security = readSecurity(userName);
//                    判空
                    if (TextUtils.isEmpty(userName)) {
                        Toast.makeText(FindPswActivity.this, "请输入您的用户名，不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!UtilsHelper.isExistUserName(FindPswActivity.this, userName)) {
                        Toast.makeText(FindPswActivity.this, "您输入的用户名不存在", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (TextUtils.isEmpty(validateName)) {
                        Toast.makeText(FindPswActivity.this, "请输入要验证的姓名", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!validateName.equals(sp_security)) {
                        Toast.makeText(FindPswActivity.this, "输入的姓名不正确，密保 不正确", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        //输入的密保正确，重新给用户设置一个密码
                        tv_reset_psw.setVisibility(View.VISIBLE);
                        tv_reset_psw.setText("初始密码：Czy34@com");
//                        将新密码  保存到   loginInfo.xml文件中    替换掉旧的 密码
                        UtilsHelper.saveUserInfo(FindPswActivity.this, userName, "Czy34@com");
                    }
                }
                break;
        }
    }


}
