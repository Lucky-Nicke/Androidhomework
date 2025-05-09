package com.boxuegu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boxuegu.R;

public class ModifyUserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_main_title, tv_save;
    private RelativeLayout rl_title_bar;
    private TextView tv_back;
    private String title, content;
    private int flag;      //flag为1时表示修改昵称，为2时表示修改签名
    private EditText et_content;
    private ImageView iv_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_info);

//        调用方法 初始化 不要 忘记 ，否则 没啥反应
        init();
    }

    /*
    * 初始化界面控件
    * */
    private void init() {
//        获取 从个人资料界面  传递过来的标题栏文本信息  和content内容（昵称或者签名）
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        flag = getIntent().getIntExtra("flag", 0);

        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText(title);
        rl_title_bar = findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back = findViewById(R.id.tv_back);

        tv_save = findViewById(R.id.tv_save);
        tv_save.setVisibility(View.VISIBLE);//设置保存按钮  为显示 状态  即可见的

        et_content = findViewById(R.id.et_content);
        iv_delete = findViewById(R.id.iv_delete);
//      判断传过来的内容 是昵称  还是   签名
        if (!TextUtils.isEmpty(content)) {
//            不为空 ，则把信息设置到 tv组件视图上
            et_content.setText(content);
//            光标 移动到 内容后面
            et_content.setSelection(content.length());
        }
        contentListener();
        tv_back.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        tv_save.setOnClickListener(this);
    }

    /*
      监听输入框中信息的功能:昵称的内容不能超过8个字，签名的内容不能超过16个字
    * contentListener()方法用于实现监听输入框中输入的信息，
    * 如果用户输入的信息超过了规定的字数，程序会调用modifyContent()方法限制用户输入的字数信息
    * */
    private void contentListener() {
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = et_content.getText(); //获取输入框中的文本信息
                int len = editable.length();                //获取输入的文本长度
                if (len > 0) {
                    iv_delete.setVisibility(View.VISIBLE); //显示清空输入框内容的图片
                } else {
                    iv_delete.setVisibility(View.GONE);     //隐藏清空输入框内容的图片
                }
                switch (flag) {
                    case 1:   //修改昵称
                        //昵称限制最多8个文字，超过8个需要截取掉多余的文字
                        if (len > 8) {
                            modifyContent(editable, 8);
                        }
                        break;
                    case 2:   //修改签名
                        //签名最多是16个文字，超过16个需要截取掉多余的文字
                        if (len > 16) {
                            modifyContent(editable, 16);
                        }
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }

    /*
      监听输入框中信息的功能：昵称的内容不能超过8个字，签名的内容不能超过16个字
    * 用于处理超过8个字的昵称与超过16个字的签名信息，将这些信息设置为规定的字数
    * */
    private void modifyContent(Editable editable, int length) {
        int selEndIndex = Selection.getSelectionEnd(editable);
        String str = editable.toString();
        //截取新字符串
        String newStr = str.substring(0, length);
        et_content.setText(newStr);
        editable = et_content.getText();
        //新字符串的长度
        int newLen = editable.length();
        if (selEndIndex > newLen) {
            selEndIndex = editable.length();
        }
        //设置新光标所在的位置
        Selection.setSelection(editable, selEndIndex);
    }

    /*
    * 实现界面控件的点击事件
    * */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                ModifyUserInfoActivity.this.finish();
                break;
            case R.id.iv_delete:  //点击 x 图片，则清空内容
                et_content.setText("");
                break;
            case R.id.tv_save:
                Intent data = new Intent();
                String etContent = et_content.getText().toString().trim();
                switch (flag) {
                    case 1:
                        if (!TextUtils.isEmpty(etContent)) {
                            EnterActivity(data, etContent, "nickName");
                        } else {
                            Toast.makeText(ModifyUserInfoActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if (!TextUtils.isEmpty(etContent)) {
                            EnterActivity(data, etContent, "signature");
                        } else {
                            Toast.makeText(ModifyUserInfoActivity.this, "签名不能为空", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                break;
        }
    }

    /*
    * 修改为 昵称或签名界面跳转到个人资料界面（信息回传）
    * 为了在个人资料修改界面修改完昵称或签名信息后，将修改后的数据信息回传到个人资料界面进行显示
    * */
    private void EnterActivity(Intent data, String etContent, String name) {
//        将 需要回传的 数据 封装到 data 对象中
        data.putExtra(name, etContent);
        setResult(RESULT_OK, data);
        Toast.makeText(ModifyUserInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        ModifyUserInfoActivity.this.finish();//关闭当前页面
    }




}
