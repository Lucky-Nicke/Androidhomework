package com.boxuegu.utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.boxuegu.bean.UserBean;
import com.boxuegu.bean.VideoBean;
import com.boxuegu.sqlite.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    private static DBUtils instance = null;
    private static SQLiteHelper helper;
    private static SQLiteDatabase db;

    /*
    * 创建 该类 的构造方法，创建数据库对象db
    * */
    public DBUtils(Context context) {
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    /*
     * 创建 DBUtils类  对象
     * */
    public static DBUtils getInstance(Context context) {
        if (instance == null) {
            instance = new DBUtils(context);
        }
        return instance;
    }

    /**
     * 保存用户信息,null代表的是如果插入的数据为空，则字段的值为null
     */
    public void saveUserInfo(UserBean bean) {
        ContentValues cv = new ContentValues();
        cv.put("userName", bean.getUserName());
        cv.put("nickName", bean.getNickName());
        cv.put("sex", bean.getSex());
        cv.put("signature", bean.getSignature());
        db.insert(SQLiteHelper.U_USERINFO, null, cv);
    }

    /*
    * 获取用户信息:
    * 由于个人资料界面需要显示用户信息，所以我们需要在DBUtils类中创建getUserInfo()方法用于获取用户信息，
    * */
    public UserBean getUserInfo(String userName) {
//        编写查询的sql
        String sql = "SELECT * FROM " + SQLiteHelper.U_USERINFO + " WHERE userName=?";
//        根据姓名去查询数据，需要传入具体的姓名
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
        UserBean bean = null;
//        遍历结果集 cursor对象，cursor对象是封装了结果集的数据
        while (cursor.moveToNext()) {
            bean = new UserBean();
//            根据字段获取字段的索引（下标 ），然后根据下标获取该下标位置上的值
            bean.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
            bean.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
            bean.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            bean.setSignature(cursor.getString(cursor.getColumnIndex("signature")));
        }
        cursor.close();
        return bean;//返回
    }

    /*
    * 修改用户信息:
    * 根据传入的用户名来更新key对应的value值，key是用户表的字段，value是字段对应的值
    * 由于个人资料界面需要修改用户的昵称、性别和签名，创建一个updateUserInfo()方法，用于修改用户信息
    * */
    public void updateUserInfo(String key, String value, String userName) {
        ContentValues cv = new ContentValues();
        cv.put(key, value);
//        userName=? 代表的更新数据的条件    new String[]{userName} 代表的更新的条件的值
        db.update(SQLiteHelper.U_USERINFO, cv, "userName=?", new String[]{userName});
    }


    public boolean hasVideoPlay(int chapterId, int videoId,String userName) {
        boolean hasVideo = false;
        String sql = "SELECT * FROM " + SQLiteHelper.U_VIDEO_PLAY_LIST
                + " WHERE chapterId=? AND videoId=? AND userName=?";
        Cursor cursor = db.rawQuery(sql, new String[] { chapterId + "",
                videoId + "" ,userName});
        if (cursor.moveToFirst()) {
            hasVideo = true;
        }
        cursor.close();
        return hasVideo;
    }

    public boolean delVideoPlay(int chapterId, int videoId,String userName) {
        boolean delSuccess=false;
        int row = db.delete(SQLiteHelper.U_VIDEO_PLAY_LIST,
                " chapterId=? AND videoId=? AND userName=?", new String[] { chapterId
                        + "", videoId + "" ,userName});
        if (row > 0) {
            delSuccess=true;
        }
        return delSuccess;
    }

    public void saveVideoPlayList(int id, String chapterName, VideoBean bean, String userName) {
        // 判断如果里面有此播放记录则需删除重新存放
        if (hasVideoPlay(id, bean.getVideoId(),userName)) {
            // 删除之前存入的播放记录
            boolean isDelete=delVideoPlay(id, bean.getVideoId(),userName);
            // 没有删除成功时，则需要调用return关键字跳出此方法不再执行下面的语句
            if(!isDelete) return;
        }
        ContentValues cv = new ContentValues();
        cv.put("userName", userName);
        cv.put("chapterId", id);
        cv.put("videoId", bean.getVideoId());
        cv.put("videoPath", bean.getVideoPath());
        cv.put("chapterName", chapterName);
        cv.put("videoName", bean.getVideoName());
        cv.put("videoIcon", bean.getVideoIcon());
        db.insert(SQLiteHelper.U_VIDEO_PLAY_LIST, null, cv);
    }

    public List<VideoBean> getVideoHistory(String userName) {
        String sql = "SELECT * FROM " + SQLiteHelper.U_VIDEO_PLAY_LIST+" WHERE userName=?";
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
        List<VideoBean> vbl = new ArrayList<>();
        VideoBean bean = null;
        while (cursor.moveToNext()) {
            bean = new VideoBean();
            bean.setVideoId(cursor.getInt(cursor.getColumnIndex("videoId")));
            bean.setVideoPath(cursor.getString(cursor.getColumnIndex("videoPath")));
            bean.setChapterName(cursor.getString(cursor.getColumnIndex("chapterName")));
            bean.setVideoName(cursor.getString(cursor.getColumnIndex("videoName")));
            bean.setVideoIcon(cursor.getString(cursor.getColumnIndex("videoIcon")));
            vbl.add(bean);
        }
        cursor.close();
        return vbl;
    }


}
