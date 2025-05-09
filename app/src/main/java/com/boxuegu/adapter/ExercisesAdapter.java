package com.boxuegu.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boxuegu.R;
import com.boxuegu.activity.ExercisesDetailActivity;
import com.boxuegu.bean.ExercisesBean;

import java.util.List;

public class ExercisesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context mContext;
    private List<ExercisesBean> ExercisesList;  //集合对象 存储习题列表数据

    /*
    * 创建该类的构造方法，因为后续创建ExercisesAdapter适配器对象时 需要 调用该构造方法
    * */
    public ExercisesAdapter(Context context) {
        this.mContext = context;
    }

    /*
    * 该方法 由于接收传递过来的习题列表数据
    * */
    public void setData(List<ExercisesBean> ExercisesList) {
        this.ExercisesList = ExercisesList; //接收传递过来的习题列表数据
        notifyDataSetChanged();            //刷新界面数据
    }

    /*
    * 创建列表条目视图
    * */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        调用 inflate 方法加载习题列表布局文件  转化为 视图对象
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.exercises_list_item, parent, false);
//       创建ViewHolder类对象 主要是为了获取  习题列表（条目）布局文件中  的相关控件
//       创建一个MyViewHolder类，调用MyViewHolder构造方法需要传入一个视图对象，才能获取条目中的那三个控件
        RecyclerView.ViewHolder holder = new MyViewHolder(itemView);
//      返回 holder对象，该对象中封装了 条目中的三个控件 对象  ，即章节序号  章节名称  习题总数量
        return holder;
    }

    /*
     * 创建一个 MyViewHolder类
     * */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_order, tv_chapterName, tv_totalNum;
//        有参构造方法
        public MyViewHolder(View view) {
            super(view);
            tv_order = view.findViewById(R.id.tv_order);//章节序号
            tv_chapterName = view.findViewById(R.id.tv_chapterName);//章节名称
            tv_totalNum = view.findViewById(R.id.tv_totalNum);//习题总数量
        }
    }

    /*
    * 将数据绑定（设置）到条目视图的三个对应的控件上
    * */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ExercisesBean bean = ExercisesList.get(position);
//        不为空
        if (bean != null) {
//          装换类型  holder对象的类型由 RecyclerView.ViewHolder 转换为 MyViewHolder类型，
//          将数据设置到控件视图上，position默认为0，章节序号从1开始
            ((MyViewHolder) holder).tv_order.setText(position + 1 + "");
            ((MyViewHolder) holder).tv_chapterName.setText(bean.getChapterName());
            ((MyViewHolder) holder).tv_totalNum.setText("共计" + bean.getTotalNum() + "题");
//            设置 章节 序号的背景图片
            switch (bean.getBackground()) {
                case 1:
                    ((MyViewHolder) holder).tv_order.setBackgroundResource(R.drawable.exercises_bg_1);
                    break;
                case 2:
                    ((MyViewHolder) holder).tv_order.setBackgroundResource(R.drawable.exercises_bg_2);
                    break;
                case 3:
                    ((MyViewHolder) holder).tv_order.setBackgroundResource(R.drawable.exercises_bg_3);
                    break;
                case 4:
                    ((MyViewHolder) holder).tv_order.setBackgroundResource(R.drawable.exercises_bg_4);
                    break;
            }
        }
        //实现每个列表条目的点击事件，因为点击习题列表会跳转到对应的习题详情界面
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果为空 不做任何 跳转操作
                if (bean == null) return;
                //不为空 跳转到习题详情界面   现在还没做习题详情功能，但 我们可以先创建 ExercisesDetailActivity
                Intent intent = new Intent(mContext, ExercisesDetailActivity.class);
                //将习题数据传递到习题详情界面中
                intent.putExtra("detailList",bean);
                mContext.startActivity(intent);
            }
        });
    }

    /*
    * 获取列表条目的总数
    * */
    @Override
    public int getItemCount() {
//        总数：即习题列表数据集合中元素的个数  三元运算符
        return ExercisesList == null ? 0 : ExercisesList.size();
    }



}
