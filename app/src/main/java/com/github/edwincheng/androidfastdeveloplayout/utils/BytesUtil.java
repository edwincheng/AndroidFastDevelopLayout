package com.github.edwincheng.androidfastdeveloplayout.utils;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-14 下午4:49
 * @ desc   : 字节转换工具类
 */
public class BytesUtil {
    /**
     * 注释：short到字节数组的转换！
     *
     * @param temp temp
     * @return
     */
    public static byte[] shortToByte(int temp) {
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = Integer.valueOf(temp & 0xff).byteValue();
            //将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }


    /**
     * 注释：int到字节数组的转换！
     *
     * @param number
     * @return
     */
    public static byte[] intToByte(int number) {
        int temp = number;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = Integer.valueOf(temp & 0xff).byteValue();
            //将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }


    /**
     * 小端byte转大端int
     * 注释：字节数组到int的转换！
     *
     * @param
     * @return
     */
    public static int byteToInt(byte[] b) {
        int s;
        int s0 = b[0] & 0xff;// 最低位
        int s1 = b[1] & 0xff;
        int s2 = b[2] & 0xff;
        int s3 = b[3] & 0xff;
        s3 <<= 24;
        s2 <<= 16;
        s1 <<= 8;
        s = s0 | s1 | s2 | s3;
        return s;
    }


    /**
     * 注释：字节数组到short的转换！
     *
     * @param b b
     * @return
     */
    public static short byteToShort(byte[] b) {
        short s;
        short s0 = (short) (b[0] & 0xff);// 最低位
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }


}
