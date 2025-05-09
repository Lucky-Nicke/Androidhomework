package com.boxuegu.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.ImageView;

public class UtilsHelper {

    /**
     * 判断SharedPreferences文件中是否存在要保存的用户名
     */
    public static boolean isExistUserName(Context context,String userName){
        boolean has_userName=false;
        SharedPreferences sp=context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String spPsw=sp.getString(userName, "");
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName=true;
        }
        return has_userName;
    }
    /*
    * 把 用户名密码 信息保存到   SharedPreferences文件  loginInfo.xml中
    * */
    public static void saveUserInfo(Context context,String userName,String psw){
        //将密码用MD5加密
        String md5Psw=MD5Utils.md5(psw);
        //获取SharedPreferences类的对象sp
        SharedPreferences sp= context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        //获取编辑器对象editor
        SharedPreferences.Editor editor=sp.edit();
        //将用户名和密码封装到编辑器对象editor中
        editor.putString(userName, md5Psw);
        editor.commit();//提交保存信息
    }



    /*
    * 3.登录界面回显用户名之后，根据用户名去（查询）读取loginInfo.xml文件中对应的密码信息
    * 调用该方法会返回一个字符串 即把密码 返回
    * */
    public static String readPsw(Context context,String userName){
//        1.先获取SharedPreferences sp 对象
        SharedPreferences sp=context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
//        2.根据用户名获取密码 信息
        String spPsw=sp.getString(userName, "");
//        把查询到的密码返回
        return spPsw;
    }
    /**
     * 4.保存登录状态和登录用户名到SharedPreferences文件中。
     * 即：当用户登录成功后，程序需要将用户名和登录状态保存到本地的SharedPreferences文件中，
     * 便于后续获取用户名与判断用户登录状态时使用，因此我们需要在UtilsHelper类中创建一个saveLoginStatus()方法，
     * 在该方法中实现保存登录状态与用户名的功能
     */
    public static void saveLoginStatus(Context context,boolean status,String userName){
        SharedPreferences sp= context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();      //获取编辑器
        editor.putBoolean("isLogin", status);            //存入boolean类型的登录状态
        editor.putString("loginUserName", userName);   //存入登录时的用户名
        editor.commit();                                     //提交修改
    }


    /*
    *步骤6：该方法中实现获取登录状态的功能
    *  由于在退出博学谷程序时，我们需要判断当前的用户是否为登录状态，
    *  所以我们需要创建一个readLoginStatus()方法，在该方法中实现获取登录状态的功能。
    *  放在该工具类中，其他地方也可以使用
    * */
    public static boolean readLoginStatus(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
//        获取用户 登录状态的值 ，默认是false
        boolean isLogin = sp.getBoolean("isLogin", false);
//        返回的 这个isLogin变量 就是用户的登录状态，登录和未登录
        return isLogin;
    }
    /*
    * 步骤7：清除登录状态与用户名
    * 即：当退出博学谷程序时，如果用户处于登录状态，此时我们需要清除用户的登录状态与用户名信息，
    * 也就是实现退出登录功能，所以我们需要创建一个clearLoginStatus()方法，在该方法中实现清除登录状态与用户名的功能
    * */
    public static void clearLoginStatus(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();  //获取编辑器
        editor.putBoolean("isLogin", false);            //清除登录状态，即key是否登录isLogin存入false值
        editor.putString("loginUserName", "");         //清除登录时的用户名   loginUserName存入空字符串
        editor.commit();                                   //提交修改
    }


    /*
    * 步骤2：获取登录时的用户名
      由于“我”的界面需要显示登录时的用户名，所以我们需要创建一个readLoginUserName()方法，
      在该方法中实现从SharedPreferences文件中获取登录时的用户名的功能。
    * */
    public static String readLoginUserName(Context context){
        SharedPreferences sp=context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String userName=sp.getString("loginUserName", "");//获取登录时的用户名
        return userName;
    }


    /*
    * 步骤3：设置A、B、C、D选项是否可被点击
     由于在习题详情界面中，当用户点击完某道题的选项后，程序不允许用户再次点击此题的选项，
     所以在程序中需要创建setABCDEnable()方法来控制A、B、C、D选项是否可被点击。
     boolean value 表示控件被点击的状态，true表示被选中
    * */
    public static void setABCDEnable(boolean value,ImageView iv_a,ImageView
            iv_b,ImageView iv_c,ImageView iv_d){
        iv_a.setEnabled(value); //设置选项A的图片是否可被点击
        iv_b.setEnabled(value); //设置选项B的图片是否可被点击
        iv_c.setEnabled(value); //设置选项C的图片是否可被点击
        iv_d.setEnabled(value); //设置选项D的图片是否可被点击
    }


}
