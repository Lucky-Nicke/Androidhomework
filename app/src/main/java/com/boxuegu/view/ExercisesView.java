package com.boxuegu.view;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boxuegu.R;
import com.boxuegu.adapter.ExercisesAdapter;
import com.boxuegu.bean.ExercisesBean;
import com.boxuegu.utils.Constant;
import com.boxuegu.utils.JsonParse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ExercisesView {

    private RecyclerView rv_list;
    private ExercisesAdapter adapter;
    private Activity mContext;
    private LayoutInflater mInflater;
    private View mCurrentView;
    private List<ExercisesBean> ebl;
    private MHandler mHandler;


    public static final int MSG_EXERCISES_OK = 1; //获取习题数据
    public ExercisesView(Activity context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }


    private void initView() {
        ebl=new ArrayList<>();
        mHandler=new MHandler();
        getExercisesData();
        mCurrentView = mInflater.inflate(R.layout.main_view_exercises, null);
        rv_list = mCurrentView.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ExercisesAdapter(mContext);
        rv_list.setAdapter(adapter);
    }

    /*
    * 请求 tomcat 中习题的数据
    * */
    private void getExercisesData() {
//        创建 OkHttpClient类对象
        OkHttpClient okHttpClient = new OkHttpClient();
//       url 即 请求数据 的接口地址 ，包括  WEB_SITE  +  REQUEST_EXERCISES_URL
        Request request = new Request.Builder().url(Constant.WEB_SITE + Constant.REQUEST_EXERCISES_URL).build();
        Call call = okHttpClient.newCall(request);
        // 接着调用  enqueue方法来 开启异步线程访问网络
        call.enqueue(new Callback() {
            //onResponse方法来获取从服务器中返回的数据
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //调用 body方法获取从服务器 返回的习题数据，变量res存储
                String res = response.body().string();
                //创建 Message对象
                Message msg = new Message();
                //Message对象的what属性的值 设置为 常量，该常量 用于 后续根据 这个值 更新习题页面的 数据信息
                msg.what = MSG_EXERCISES_OK;
                //obj的值 设置为res  ,服务器 返回的习题数据
                msg.obj = res;
                //最后调用 sendMessage方法 将 msg对象 传递到 主线程中
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call call, IOException e){
            }

        });
    }

    /*
    *  在主线程中 接收 msg对象，更新界面的 数据信息
    * */
    class MHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
//            判断 msg对象 what属性的值 是否为  MSG_EXERCISES_OK
//            如果是 则 说明 传递过来的是  习题界面 的数据
            switch (msg.what) {
                case MSG_EXERCISES_OK:
                    //然后 通过if 判断 obj的值是否为 null
                    if (msg.obj != null) {
                        //不为空  则获取 msg对象中 obj的值, 赋值 ，这个vlResult变量的值就是 习题界面中的数据
                        String vlResult = (String) msg.obj;
                        //调用 clear方法清除 ebl习题集合对象   中的数据
                        if (ebl!=null)ebl.clear();
                        //调用JsonParse类的getExercisesList方法来解析获取的JSON数据
                        ebl = JsonParse.getInstance().getExercisesList(vlResult);
                        //把解析之后 的数据 设置到 适配器上，数据就会显示到习题界面上
                        adapter.setData(ebl);
                    }
                    break;
            }
        }
    }

    /**
     * 获取习题界面
     */
    public View getView() {
        if (mCurrentView == null) {
            initView(); //初始化界面控件
        }
        return mCurrentView;
    }

    /**
     * 显示习题界面
     */
    public void showView() {
        if (mCurrentView == null) {
            initView(); //初始化界面控件
        }
        mCurrentView.setVisibility(View.VISIBLE); //显示当前界面
    }


}
