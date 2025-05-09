package com.boxuegu.activity;

import android.content.Intent;
import android.os.Bundle;

import com.boxuegu.utils.MD5Utils;
import com.boxuegu.utils.UtilsHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.boxuegu.R;
/*
* 用户登录
* */
public class LoginActivity extends AppCompatActivity  implements View.OnClickListener {

//    声明全局组件对象和变量
    private TextView tv_main_title;
    private TextView tv_back, tv_register, tv_find_psw;
    private Button btn_login;
    private String userName, psw, spPsw;
    private EditText et_user_name, et_psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//       调用 获取界面控件的方法不要忘记了
        init();

    }

    /**
     * 1.获取界面控件（获取组件对象）
     */
    private void init() {
//        获取界面标题
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_back = findViewById(R.id.tv_back);//获取返回按钮
        tv_register = findViewById(R.id.tv_register);//立即注册
        tv_find_psw = findViewById(R.id.tv_find_psw);//找回密码
        btn_login = findViewById(R.id.btn_login); //登录按钮
        et_user_name = findViewById(R.id.et_user_name);//用户名
        et_psw = findViewById(R.id.et_psw);//密码
//       注册：给按钮添加事件监听器
//       使用当前类实现事件监听器 implements View.OnClickListener
        tv_back.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_find_psw.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

   /*
   * 2.获取注册界面 会传过来的用户名
   * 然后 根据用户名去（查询）读取loginInfo.xml文件中对应的密码信息
   * 即调用 UtilsHelper 中的 readPsw方法去查询
   * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        如果回传过来的数据（有用户名或者密码  或者都有） Intent data 不为空
        if(data!=null){
            //则获取从注册界面传递过来的用户名
            String userName =data.getStringExtra("userName");
//            如果用户名不为空（有数据回传不一定用户名不为空，可能只有密码 回传成功）
            if(!TextUtils.isEmpty(userName)){
//                把用户名设置到 用户名控件上
                et_user_name.setText(userName);
                //设置光标的位置，即把光标设置到用户名的后面
                et_user_name.setSelection(userName.length());
            }
        }
    }

    /*
    *5.在onClick方法中实现具体的业务逻辑
    * */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:     //"返回"按钮的点击事件
                this.finish();
                break;
            case R.id.tv_register: //"立即注册"文本的点击事件
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                之所以 选择 startActivityForResult方法 ，是因为我们需要把注册页面的用户名返回到登录界面，接收
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_find_psw://"找回密码？"文本的点击事件
                //跳转到找回密码界面，暂时先创建 该界面，后续再完成 找回密码 业务功能
                Intent findPswIntent = new Intent(LoginActivity.this,FindPswActivity.class);
                startActivity(findPswIntent);
                break;
            case R.id.btn_login: //"登录"按钮的点击事件
//                获取用户输入的用户名和密码
                userName=et_user_name.getText().toString().trim();
                psw=et_psw.getText().toString().trim();
//                对用户输入的密码 进行md5加密
                String md5Psw= MD5Utils.md5(psw);
//                根据用户名 查询  loginInfo.xml文件中对应的  密码
                spPsw=UtilsHelper.readPsw(LoginActivity.this,userName);
//              判空 操作
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if(TextUtils.isEmpty(spPsw)){
//                     loginInfo.xml中 不存在 用户名 对应的密码，说明该用户不存在
                    Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                    return;
                } else if((!TextUtils.isEmpty(spPsw)&&!md5Psw.equals(spPsw))){
//                    不为空，但是用户输入的密码 和查询到的密码 不一致 ，即用户输入的密码不一致
                    Toast.makeText(LoginActivity.this, "输入的密码不正确", Toast.LENGTH_SHORT).show();
                    return;
                }else if(md5Psw.equals(spPsw)){
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //登录成功，需要保存登录状态和登录的用户名到  SharedPreferences文件  loginInfo.xml中，方便后续需要判断登录状态时使用
                    UtilsHelper.saveLoginStatus(LoginActivity.this,true,userName);
                    //并且要把登录成功的状态传递到MainActivity中，方便后续需要判断登录状态时使用
                    Intent data=new Intent();
                    data.putExtra("isLogin", true);
                    setResult(RESULT_OK, data);
//                    关闭登录界面
                    LoginActivity.this.finish();
                }
                break;
        }
    }



}
