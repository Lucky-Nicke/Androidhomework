package com.boxuegu.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class MD5Utils {
    /**
     * 创建MD5加算法的密方法md5()，将一个字符串（text）加密，然后进行返回
     * 传入 字符串数据（text）----->   加密 -----> 返回加密后的字符串数据
     */
    public static String md5(String text) {
        MessageDigest digest = null;
        try {
//            1.调用getInstance方法获取MD5算法的对象
            digest = MessageDigest.getInstance("md5");
//            2.MD5算法的对象digest调用digest（）方法 将字符串转化为 字节数组
            byte[] result = digest.digest(text.getBytes());
//            创建 字符串凭借对象 sb
            StringBuilder sb = new StringBuilder();
//            遍历循环输出
            for (byte b : result) {
                //将字节byte转换为int类型的数据
//                是位与，byte转int时，需要&0xff，补足前面的24位。因此如果byte是负数，转成int时可能会变成整数。
                int number = b & 0xff;
                //将数组中的 int类型的数据转换为十六进制的字符串数 hex
                String hex = Integer.toHexString(number);
//
                if (hex.length() == 1) {
//                    并且将数据保存（追加到）到 sb对象中
                    sb.append("0" + hex);
                } else {
                    sb.append(hex);
                }
            }
//            将sb对象中的加密的数据转化为字符串的形式 ，并且返回
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
