package com.boxuegu.activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.boxuegu.R;
import com.boxuegu.adapter.ExercisesDetailAdapter;
import com.boxuegu.bean.ExercisesBean;
import com.boxuegu.bean.ExercisesDetailBean;
import com.boxuegu.utils.UtilsHelper;

import java.util.List;

public class ExercisesDetailActivity extends AppCompatActivity {

    private TextView tv_main_title;
    private TextView tv_back;
    private RelativeLayout rl_title_bar;
    private ListView lv_list;
    private String title;
    private ExercisesBean bean;
    private List<ExercisesDetailBean> detailList;
    private ExercisesDetailAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_detail);
        //获取从习题界面传递过来的习题数据
        // ExercisesBean习题实体类（章节id 章节名称 习题总数 章节序号背景 以及习题详情列表）
        bean = (ExercisesBean) getIntent().getSerializableExtra("detailList");
        if (bean != null) {
            title = bean.getChapterName();      //获取习题所在的章节名称
            detailList = bean.getDetailList(); //获取习题详情界面的数据
        }
        init();
    }

    /*
    * 获取界面控件
    * */
    private void init() {
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_back = findViewById(R.id.tv_back);
        rl_title_bar = findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        lv_list = findViewById(R.id.lv_list);
        TextView tv = new TextView(this);
        tv.setTextColor(Color.parseColor("#000000"));
        tv.setTextSize(16.0f); //16.0f 等效于 16sp
        tv.setText("一、选择题");
        tv.setPadding(10, 15, 0, 0);
        lv_list.addHeaderView(tv); //将控件tv添加到列表控件lv_list的上方，这样列表在滑动时，题目也会一起滑动2
        tv_main_title.setText(title);
        //注册返回 按钮 的事件监听器
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExercisesDetailActivity.this.finish();
            }
        });

        /*
        * 实现  ABCD 四个选项的 点击事件
        * */
        //首先  创建适配器对象  第一个参数:上下文环境 第二个参数:实现 OnSelectListener接口 里面的四个方法
        adapter = new ExercisesDetailAdapter(ExercisesDetailActivity.this,
                new ExercisesDetailAdapter.OnSelectListener() {
                    @Override
                    public void onSelectA(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                        //判断如果答案是否为1，即A选项
                        SelectValue(position,1);//用户选择A选项
                        switch (detailList.get(position).getAnswer()) { //detailList.get(position).getAnswer() 获取题目的答案是哪个选项
                           //判断  用户选择的A选项  和 真实答案ABCD四个选项   其他几个 同理
                            case 1:
                                iv_a.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 2:
                                iv_a.setImageResource(R.drawable.exercises_error_icon);
                                iv_b.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 3:
                                iv_a.setImageResource(R.drawable.exercises_error_icon);
                                iv_c.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 4:
                                iv_a.setImageResource(R.drawable.exercises_error_icon);
                                iv_d.setImageResource(R.drawable.exercises_right_icon);
                                break;
                        }
                        UtilsHelper.setABCDEnable(false, iv_a, iv_b, iv_c, iv_d);
                    }

                    @Override
                    public void onSelectB(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                        //判断如果答案不是2即B选项
                        SelectValue(position,2);
                        switch (detailList.get(position).getAnswer()) {
                            //判断  用户选择的B选项  和 真实答案ABCD四个选项
                            case 1:
                                iv_a.setImageResource(R.drawable.exercises_right_icon);
                                iv_b.setImageResource(R.drawable.exercises_error_icon);
                                break;
                            case 2:
                                iv_b.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 3:
                                iv_b.setImageResource(R.drawable.exercises_error_icon);
                                iv_c.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 4:
                                iv_b.setImageResource(R.drawable.exercises_error_icon);
                                iv_d.setImageResource(R.drawable.exercises_right_icon);
                                break;
                        }
                        UtilsHelper.setABCDEnable(false, iv_a, iv_b, iv_c, iv_d);
                    }

                    @Override
                    public void onSelectC(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                        //判断如果答案不是3即C选项
                        SelectValue(position,3);
                        switch (detailList.get(position).getAnswer()) {
                            case 1:
                                iv_a.setImageResource(R.drawable.exercises_right_icon);
                                iv_c.setImageResource(R.drawable.exercises_error_icon);
                                break;
                            case 2:
                                iv_b.setImageResource(R.drawable.exercises_right_icon);
                                iv_c.setImageResource(R.drawable.exercises_error_icon);
                                break;
                            case 3:
                                iv_c.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 4:
                                iv_c.setImageResource(R.drawable.exercises_error_icon);
                                iv_d.setImageResource(R.drawable.exercises_right_icon);
                                break;
                        }
                        UtilsHelper.setABCDEnable(false, iv_a, iv_b, iv_c, iv_d);
                    }

                    @Override
                    public void onSelectD(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                        //判断如果答案不是4即D选项
                        SelectValue(position,4);
                        switch (detailList.get(position).getAnswer()) {
                            case 1:
                                iv_a.setImageResource(R.drawable.exercises_right_icon);
                                iv_d.setImageResource(R.drawable.exercises_error_icon);
                                break;
                            case 2:
                                iv_d.setImageResource(R.drawable.exercises_error_icon);
                                iv_b.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 3:
                                iv_d.setImageResource(R.drawable.exercises_error_icon);
                                iv_c.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 4:
                                iv_d.setImageResource(R.drawable.exercises_right_icon);
                                break;
                        }
                        UtilsHelper.setABCDEnable(false, iv_a, iv_b, iv_c, iv_d);
                    }

                });

        //将数据设置到适配器中
        adapter.setData(detailList);
        //绑定：将 适配器 设置到列表控件上，数据就可以在组建视图上显示出来了
        lv_list.setAdapter(adapter);
    }

    /*
    *该方法是用于设置 select 属性的值
    * select为0表示所选项是对的，1表示选中的A选项是错的，以此类推
    * position代表习题在列表中的位置 ， option表示用户选的 选项的值
    * */
    private void SelectValue(int position,int option){
        //判断 当前习题的 答案 与 用户选中的答案 是否一致
        if (detailList.get(position).getAnswer() != option) {
            //不一致 则设置属性 select 的值为 当前选项的序号 即 option
            detailList.get(position).setSelect(option);
        } else {
            detailList.get(position).setSelect(0);//0表示用户选的选项是正确的
        }
    }


}
