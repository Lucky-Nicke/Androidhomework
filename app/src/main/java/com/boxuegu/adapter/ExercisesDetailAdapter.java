package com.boxuegu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boxuegu.R;
import com.boxuegu.bean.ExercisesDetailBean;
import com.boxuegu.utils.UtilsHelper;

import java.util.ArrayList;
import java.util.List;

public class ExercisesDetailAdapter extends BaseAdapter {

    private Context mContext;
//    声明存储 item条目的 集合（容器）对象
    private List<ExercisesDetailBean> edbl;
    //记录点击的位置
    private ArrayList<String> selectedPosition = new ArrayList<String>();
    //
    private OnSelectListener onSelectListener;

//    创建 构造方法 依赖 实例化 适配器对象
    public ExercisesDetailAdapter(Context context, OnSelectListener onSelectListener) {
        this.mContext = context;
        this.onSelectListener = onSelectListener;
    }

    /*
    * 该方法用于传递习题详情页面的数据
    * */
    public void setData(List<ExercisesDetailBean> edbl) {
        this.edbl = edbl;         //接收传递过来的习题数据
        notifyDataSetChanged();   //刷新界面信息
    }

//    重写 父接口BaseAdapter中的四个方法
//    获取条目的总数，即获取集合中元素的个数
    @Override
    public int getCount() {
        return edbl == null ? 0 : edbl.size();
    }
//    根据条目的下标（索引/位置）获取该元素（即 条目对象 ）
    @Override
    public ExercisesDetailBean getItem(int position) {
        return edbl == null ? null : edbl.get(position);
    }
//    根据下标（id）获取条目在listview列表中的位置
    @Override
    public long getItemId(int position) {
        return position;
    }
//    获取 item视图 （即获取 显示条目信息的视图，即每一道题所包含的题目信息、abcd四张图片、每个选项的文本信息）
//    convertView对象 为复用item视图对象，说白了就是 条目视图对象（显示条目信息的视图，可以理解为 每一道题）
//    getView方法主要是用于：设置习题详情列表界面的数据（题干、四个选项的图片、四个选项的文本信息）
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        //为空，则创建对象 然后获取 item中的 相关 组件对象
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.exercises_detail_list_item, null);
             //获取题干组件对象
            vh.subject = convertView.findViewById(R.id.tv_subject);
            //获取四个选项的文本
            vh.tv_a = convertView.findViewById(R.id.tv_a);
            vh.tv_b = convertView.findViewById(R.id.tv_b);
            vh.tv_c = convertView.findViewById(R.id.tv_c);
            vh.tv_d = convertView.findViewById(R.id.tv_d);
            //获取四个选项的图片
            vh.iv_a = convertView.findViewById(R.id.iv_a);
            vh.iv_b = convertView.findViewById(R.id.iv_b);
            vh.iv_c = convertView.findViewById(R.id.iv_c);
            vh.iv_d = convertView.findViewById(R.id.iv_d);
            //调用setTag方法 将ViewHolder类的对象vh 存放到 convertView对象中
            //说白了就是每一道题（即item条目）包含了题干、四个选项的图片、四个选项的文本信息
            convertView.setTag(vh);
        } else {
            //不为空 则直接调用getTag即可 强转类型
            //然后 对象 直接就可以调用 item  中的相关组件对象  组视图上设置相关数据即可
            vh = (ViewHolder) convertView.getTag();
        }

        //getItem方法获取到item条目对象（即包含了题干、四个选项的图片、四个选项的文本信息）
        //把item条目对象中包含的数据 保存到  详情实体类 bean 中
        final ExercisesDetailBean bean = getItem(position);
        //判空
        if (bean != null) {
            //不为空  视图上设置对应的数据
            vh.subject.setText(bean.getSubject()); //设置题干信息
            vh.tv_a.setText(bean.getA());            //设置A选项数据
            vh.tv_b.setText(bean.getB());            //设置B选项数据
            vh.tv_c.setText(bean.getC());            //设置C选项数据
            vh.tv_d.setText(bean.getD());            //设置D选项数据
        }
        //selectedPosition对象 表示点击的位置
        //如果当前的习题没有被选择过，则true代表可以被点击选择 选项
        if (!selectedPosition.contains("" + position)) {
            vh.iv_a.setImageResource(R.drawable.exercises_a);
            vh.iv_b.setImageResource(R.drawable.exercises_b);
            vh.iv_c.setImageResource(R.drawable.exercises_c);
            vh.iv_d.setImageResource(R.drawable.exercises_d);
            UtilsHelper.setABCDEnable(true, vh.iv_a, vh.iv_b, vh.iv_c, vh.iv_d);
        } else {
            //当前的习题没有被选择过，不能再次选择
            UtilsHelper.setABCDEnable(false, vh.iv_a, vh.iv_b, vh.iv_c, vh.iv_d);
            //根据  select的值判断 是否是对的，即为0表示所选项是对的 ，1234表示选择的abcd选项是错的，
            // 可以去ExercisesDetailBean实体类中 查看
            //注意 case的值  和  bean.getAnswer()的值：1代表A选项，以此类推
            switch (bean.getSelect()) {
                case 0://0表示所选项是对的，即用户选对了，但是不知道用户选了 abcd哪一个，需要进一步判断四个选项
                    //用户选择的选项是正确的
                    if (bean.getAnswer() == 1) {
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    } else if (bean.getAnswer() == 2) {
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    } else if (bean.getAnswer() == 3) {
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    } else if (bean.getAnswer() == 4) {
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                case 1:
                    //用户选择的选项A是错误的
                    vh.iv_a.setImageResource(R.drawable.exercises_error_icon);
                    if (bean.getAnswer() == 2) {
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    } else if (bean.getAnswer() == 3) {
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    } else if (bean.getAnswer()== 4) {
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                case 2:
                    //用户选择的选项B是错误的
                    vh.iv_b.setImageResource(R.drawable.exercises_error_icon);
                    if (bean.getAnswer() == 1) {
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    } else if (bean.getAnswer() == 3) {
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    } else if (bean.getAnswer() == 4) {
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                case 3:
                    //用户选择的选项C是错误的
                    vh.iv_c.setImageResource(R.drawable.exercises_error_icon);
                    if (bean.getAnswer() == 1) {
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    } else if (bean.getAnswer() == 2) {
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    } else if (bean.getAnswer() == 4) {
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                case 4:
                    //用户选择的选项D是错误的
                    vh.iv_d.setImageResource(R.drawable.exercises_error_icon);
                    if (bean.getAnswer() == 1) {
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                    } else if (bean.getAnswer() == 2) {
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                    } else if (bean.getAnswer() == 3) {
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                default:
                    break;
            }
        }

        /*
        *设置习题列表中每个选项的点击事件监听器
        *习题详情列表中的每个习题选项都需要实现点击事件
        * */
        //A选项的点击事件
        vh.iv_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加 position 是用于记录 该选项被点击过
                selectedPosition(position);
                //调用接口中的onSelectA()方法，在该方法中实现A选项的点击事件
                onSelectListener.onSelectA(position, vh.iv_a, vh.iv_b, vh.iv_c, vh.iv_d);
            }
        });
        //B选项的点击事件
        vh.iv_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加 position 是用于记录 该选项被点击过
                selectedPosition(position);
                //调用接口中的onSelectB()方法，在该方法中实现B选项的点击事件
                onSelectListener.onSelectB(position, vh.iv_a, vh.iv_b, vh.iv_c, vh.iv_d);
            }
        });
        //C选项的点击事件
        vh.iv_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加 position 是用于记录 该选项被点击过
                selectedPosition(position);
                //调用接口中的onSelectC()方法，在该方法中实现C选项的点击事件
                onSelectListener.onSelectC(position, vh.iv_a, vh.iv_b, vh.iv_c, vh.iv_d);
            }
        });
        //D选项的点击事件
        vh.iv_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加 position 是用于记录 该选项被点击过
                selectedPosition(position);
                //调用接口中的onSelectD()方法，在该方法中实现D选项的点击事件
                onSelectListener.onSelectD(position, vh.iv_a, vh.iv_b, vh.iv_c, vh.iv_d);
            }
        });
        //返回  convertView 条目对象（此时以及包含了数据，即 组件视图上已经设置了数据）
        return convertView;
    }

    /*
    * 创建 ViewHolder 类  主要是获取 组件对象
    * */
    class ViewHolder {
        //subject为题干（题目），剩下的  四个为abcd四个选项对应的文本信息
        public TextView subject, tv_a, tv_b, tv_c, tv_d;
        //显示abcd四个选项的图片组件
        public ImageView iv_a, iv_b, iv_c, iv_d;
    }

    /*
    * 该方法用于 将当前被选中的选项的位置position  添加到集合 selectedPosition对象中
    * */
    private void selectedPosition(int position){
        //不包含则添加到集合中，包含则不做任何处理
        if (!selectedPosition.contains("" + position)) {
            selectedPosition.add(position + "");
        }
    }

    /*
    * 创建监听 选择题选项的接口OnSelectListener
    * 由于当用户点击习题详情界面中某个选择题的选项后，程序需要设置该选择题的A、B、C、D选项的图片
    * position 即 选中的选项图片
    * */
    public interface OnSelectListener {
        void onSelectA(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d);
        void onSelectB(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d);
        void onSelectC(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d);
        void onSelectD(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d);
    }

}
