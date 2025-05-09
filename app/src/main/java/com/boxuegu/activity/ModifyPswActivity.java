package com.boxuegu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.boxuegu.R;
import com.boxuegu.utils.MD5Utils;
import com.boxuegu.utils.UtilsHelper;


public class ModifyPswActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_main_title;
    private TextView tv_back;
    private EditText et_original_psw,et_new_psw,et_new_psw_again;
    private Button btn_save;
    private String originalPsw, newPsw, newPswAgain;
    private String spOriginalPsw, userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psw);
        init();
//        调用 readLoginUserName方法获取当前登录用户的 用户名信息
        userName = UtilsHelper.readLoginUserName(this);
//        根据用户名 来 获取登录用户的  密码信息
        spOriginalPsw = UtilsHelper.readPsw(this, userName);
    }

    /*
    * 初始化组件对象
    * */
    private void init(){
        tv_main_title= findViewById(R.id.tv_main_title);
        tv_main_title.setText("修改密码");
        tv_back= findViewById(R.id.tv_back);
        et_original_psw= findViewById(R.id.et_original_psw);
        et_new_psw= findViewById(R.id.et_new_psw);
        et_new_psw_again= findViewById(R.id.et_new_psw_again);
        btn_save= findViewById(R.id.btn_save);
        tv_back.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    /*
    * 自身类作为事件监听器，实现具体的业务逻辑，判断 用户点击 哪个按钮 就执行 具体业务
    * */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                ModifyPswActivity.this.finish();
                break;
            case R.id.btn_save:
                getEditString();
                if (TextUtils.isEmpty(originalPsw)) {
                    Toast.makeText(ModifyPswActivity.this, "请输入原始密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!MD5Utils.md5(originalPsw).equals(spOriginalPsw)) {
                    Toast.makeText(ModifyPswActivity.this, "输入的密码与原始密码不相同", Toast.LENGTH_SHORT).show();
                    return;
                } else if (MD5Utils.md5(newPsw).equals(spOriginalPsw)) {
                    Toast.makeText(ModifyPswActivity.this, "输入的新密码与原始密码不能相同", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(newPsw)) {
                    Toast.makeText(ModifyPswActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(newPswAgain)) {
                    Toast.makeText(ModifyPswActivity.this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!newPsw.equals(newPswAgain)) {
                    Toast.makeText(ModifyPswActivity.this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(ModifyPswActivity.this, "新密码设置成功", Toast.LENGTH_SHORT).show();
                    //保存新密码到SharedPreferences文件中
                    UtilsHelper.saveUserInfo(ModifyPswActivity.this, userName, newPsw);
                    Intent intent = new Intent(ModifyPswActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SettingActivity.instance.finish(); //关闭设置界面
                    ModifyPswActivity.this.finish();    //关闭修改密码界面
                }
                break;
        }
    }


    /**
     * 获取界面输入框控件上的字符串
     */
    private void getEditString() {
//        原始密码
        originalPsw = et_original_psw.getText().toString().trim();
        //        新 密码
        newPsw = et_new_psw.getText().toString().trim();
        //        确认 新密码
        newPswAgain = et_new_psw_again.getText().toString().trim();
    }


}
