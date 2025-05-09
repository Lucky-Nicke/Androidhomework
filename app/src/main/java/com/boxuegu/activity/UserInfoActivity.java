package com.boxuegu.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boxuegu.R;
import com.boxuegu.bean.UserBean;
import com.boxuegu.utils.DBUtils;
import com.boxuegu.utils.UtilsHelper;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_back;
    private TextView tv_main_title;
    private TextView tv_nickName, tv_signature, tv_user_name, tv_sex;
    private RelativeLayout rl_nickName, rl_sex, rl_signature, rl_title_bar;
    private String spUserName;
    private static final int CHANGE_NICKNAME = 1;   //修改昵称的自定义常量
    private static final int CHANGE_SIGNATURE = 2;  //修改签名的自定义常量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        //从SharedPreferences文件中获取登录时的用户名，存储在临时 变量spUserName中
        spUserName = UtilsHelper.readLoginUserName(this);
        init();
        setData();
        setListener();
    }

    /**
     * 初始化界面控件
     */
    private void init() {
        tv_back = findViewById(R.id.tv_back);
        tv_main_title = findViewById(R.id.tv_main_title);//标题栏文本控件
        tv_main_title.setText("个人资料");
        rl_title_bar = findViewById(R.id.title_bar);//标题栏布局
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF")); //标题栏背景色 透明
//      获取 用户信息的 四个 布局 控件
        rl_nickName = findViewById(R.id.rl_nickName);
        rl_sex = findViewById(R.id.rl_sex);
        rl_signature = findViewById(R.id.rl_signature);
//      获取  显示 用户信息的  四个控件
        tv_nickName = findViewById(R.id.tv_nickName);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_sex = findViewById(R.id.tv_sex);
        tv_signature = findViewById(R.id.tv_signature);
    }

    /*
     * 设置界面数据
     * 由于个人资料界面上需要显示用户信息的数据，所以我们需要在该方法中设置界面的数据，
     * */
    private void setData() {
        UserBean bean = null;
//        获取用户信息，根据登录的 用户名去数据库 查询
        bean = DBUtils.getInstance(this).getUserInfo(spUserName);
        // 首先判断一下数据库是否有数据
        if (bean == null) {//为空
            bean = new UserBean();
            bean.setUserName(spUserName);
            bean.setNickName("小智");
            bean.setSex("男");
            bean.setSignature("世界这么大，我想去看看");
            // 然后  保存用户信息到数据库中
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
//        bean不为空，根据用户名查询到对象了
        tv_nickName.setText(bean.getNickName());
        tv_user_name.setText(bean.getUserName());
        tv_sex.setText(bean.getSex());
        tv_signature.setText(bean.getSignature());
    }

    /*
     * 实现修改用户性别的功能
     * 当用户点击个人资料界面中的性别条目时，程序会弹出一个对话框，在对话框中对用户的性别进行选择
     * */
    private void sexDialog(String sex) {
        //声明一个性别编号变量  默认被选中的 性别0为男，1为女
        int checkItem = 0;
//        判断
        if ("男".equals(sex)) {
            checkItem = 0;
        } else if ("女".equals(sex)) {
            checkItem = 1;
        }
//        定义一个 字符串数组 存储数据
        final String items[] = {"男", "女"};
//        获取对话框的构造器  先得到构造器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("性别"); // 设置标题
//        setSingleChoiceItems实现 单选对话框功能 ，匿名内部类
//        items表示对话框中的数据 即 items[] = {"男", "女"}，checkItem表示被选中的数据 即性别0为男，1为女
        builder.setSingleChoiceItems(items, checkItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        实现 单选对话框的 点击事件
                        dialog.dismiss(); //关闭单选对话框
//                        提示用户   which表示选择的性别编号的值
                        Toast.makeText(UserInfoActivity.this, items[which], Toast.LENGTH_SHORT).show();
//                        调用 setSex 方法设置 显示到 界面上的性别数据
                        setSex(items[which]);
                    }
                });
//     最后不要忘记 设置并且 显示 该 对话框
        builder.create().show();
    }

    /*
     * 该方法用于实现将选择的性别数据显示到界面上并更新数据库中的性别信息，
     * */
    private void setSex(String sex) {
        tv_sex.setText(sex);
        // 更新数据库中的性别数据，即根据当前登录用户的姓名更新 表字段 sex的 值为 sex（男 或者 女）
        DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("sex", sex, spUserName);
    }

    /*
    * 设置界面控件的点击事件监听器
    * */
    private void setListener() {
        tv_back.setOnClickListener(this);
        rl_nickName.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_signature.setOnClickListener(this);
    }

    /*
    * 然后 实现界面控件的点击事件
    * 个人资料界面中的“返回”按钮、昵称条目、性别条目和签名条目都需要实现点击功能
    * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:      // "返回"按钮的点击事件
                this.finish();
                break;
            case R.id.rl_nickName: // 昵称条目的点击事件
                String name = tv_nickName.getText().toString(); //获取昵称控件上的数据
                Bundle bdName = new Bundle();
                bdName.putString("content", name);                 //传递界面上的昵称数据
                bdName.putString("title", "昵称");                  //传递个人资料修改界面的标题
                bdName.putInt("flag", 1);                            //flag传递1时表示修改昵称
                enterActivityForResult(ModifyUserInfoActivity.class, CHANGE_NICKNAME, bdName);
                break;
            case R.id.rl_sex:       // 性别条目的点击事件
                String sex = tv_sex.getText().toString(); // 获取性别控件上的数据
                sexDialog(sex);     // 设置性别数据
                break;
            case R.id.rl_signature:// 签名条目的点击事件
                String signature = tv_signature.getText().toString(); //获取签名控件上的数据
                Bundle bdSignature = new Bundle();
                bdSignature.putString("content", signature);            //传递界面上的签名数据
                bdSignature.putString("title", "签名");                   //传递个人资料修改界面的标题
                bdSignature.putInt("flag", 2);                             //flag传递2时表示修改签名
                enterActivityForResult(ModifyUserInfoActivity.class, CHANGE_SIGNATURE, bdSignature);
                break;
            default:
                break;
        }
    }

    /**
     * 获取回传数据时需使用的跳转方法，第一个参数to表示需要跳转到的界面（跳到 修改签名 界面或者昵称界面），
     * 第2个参数requestCode表示一个请求码，第3个参数b表示跳转时传递的数据
     */
    public void enterActivityForResult(Class<?> to, int requestCode, Bundle b) {
        Intent i = new Intent(this, to);
        i.putExtras(b);
        startActivityForResult(i, requestCode);
    }

    /*
    *接收个人资料修改界面回传过来的数据
    * 为了个人资料修改界面修改完昵称或签名信息后，会将修改后的数据信息回传到个人资料界面进行显示
    * */
    private String new_info; //修改后的最新数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHANGE_NICKNAME:  //个人资料修改界面回传过来的昵称数据
                if (data != null) {
                    new_info = data.getStringExtra("nickName");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    }
                    tv_nickName.setText(new_info);
                    //更新数据库中的昵称字段
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo(
                            "nickName", new_info, spUserName);
                }
                break;
            case CHANGE_SIGNATURE: //个人资料修改界面回传过来的签名数据
                if (data != null) {
                    new_info = data.getStringExtra("signature");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    }
                    tv_signature.setText(new_info);
                    //更新数据库中的签名字段
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo(
                            "signature", new_info, spUserName);
                }
                break;
        }
    }


}
