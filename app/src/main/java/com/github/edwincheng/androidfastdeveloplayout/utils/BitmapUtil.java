package com.github.edwincheng.androidfastdeveloplayout.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import com.github.edwincheng.androidfastdeveloplayout.global.Config;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {

    /**
     * 得到当前view的截图
     * @param v view
     * @return Bitmap
     */
    public static Bitmap getSnapshot(View v){
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        c.translate(-v.getScrollX(), -v.getScrollY());
        v.draw(c);
        return screenshot;
    }


    /**
     * 合并两张bitmap为一张
     * @param background background
     * @return Bitmap
     */
    public static Bitmap combineBitmap(Bitmap background, int width, int height) {
        if (background == null) {
            return Bitmap
                    .createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        Bitmap newmap = Bitmap
                .createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(background, width / 2 - bgWidth / 2, height / 2 - bgHeight / 2, null);
        canvas.save();
        canvas.restore();
        return newmap;
    }


    private static byte[] bitampToByteArray(Bitmap bitmap){
        byte[] array = null;
        try{
            if (null != bitmap){
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                array = os.toByteArray();
                os.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return array;
    }


    /**
     * 将图片保存到本地
     * @param bmp 位图
     * @param strPath 路径
     */
    public static void saveBitmapToSDCard(Bitmap bmp, String strPath){
        if (null != bmp && null != strPath){
            try{
                File file = new File(strPath);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = BitmapUtil.bitampToByteArray(bmp);
                fos.write(buffer);
                fos.close();
            }
            catch (Exception e){
                e.printStackTrace();
                Log.e("tag", "Exception"+e.toString());
            }
        }
    }


    /**
     * 强制缩放n倍本地图片
     * @param strPath 路径
     * @return bitmap
     */
    public static Bitmap loadBitmapFromSDCard(String strPath, int n){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(strPath, options);
        options.inSampleSize = n;   //width，hight缩小为原来的n倍
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(strPath, options);
    }


    /**
     * 加载原图，大图很容易oom，慎用
     * @param strPath 路径
     * @return bitmap
     */
    public static Bitmap loadBitmapFromSDCard(String strPath){
        File file = new File(strPath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            assert fis != null;
            return BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeFile(strPath, options);
    }


    /**
     * 根据宽高缩放图片，在传入时请确认宽高不为0
     * @param strPath 路径
     * @param reqWidth 控件宽
     * @param reqHeight 控件高
     * @return bitmap
     */
    public static Bitmap loadBitmapFromSDCard(String strPath, int reqWidth, int reqHeight){
        File file = new File(strPath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(strPath, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        try {
            assert fis != null;
            return BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeFile(strPath, options);
    }


    /**
     * 根据宽高计算出需要缩放的倍数
     * @param options options
     * @param reqWidth 宽
     * @param reqHeight 高
     * @return 倍数
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }


    /**
     * 压缩图片
     * @param bitmap 源图片
     * @param width 想要的宽度
     * @param height 想要的高度
     * @param isAdjust 是否自动调整尺寸, true图片就不会拉伸，false严格按照你的尺寸压缩
     * @return Bitmap
     */
    public static Bitmap reduce(Bitmap bitmap, int width, int height, boolean isAdjust) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        float sx;
        float sy;
        sx = ((float)width)/bitmapWidth;
        sy = ((float)height)/bitmapHeight;
        if (isAdjust) {// 如果想自动调整比例，不至于图片会拉伸
            sx = (sx < sy ? sx : sy);sy = sx;// 哪个比例小一点，就用哪个比例
        }
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);// 调用api中的方法进行压缩，就大功告成了
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    /**
     * 根据传进来的宽高生成一个新的bitmap
     * @param b
     * @param x
     * @param y
     * @return
     */
    public static Bitmap creatNewBitmap(Bitmap b, float x, float y) {
        int w = b.getWidth();
        int h = b.getHeight();
        float sx = (float) x / w;
        float sy = (float) y / h;
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);  // 长和宽放大缩小的比例
        Bitmap newBitmap = Bitmap.createBitmap(b, 0, 0, w,
                h, matrix, true);
        return newBitmap;
    }

    /** 缩略图 */
    public static Bitmap bitmapThumbnail(Bitmap bitmap, int targetWidth, int targetHeight){
        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
    }

    /** 缩略图,暂时不用：耗时 */
    @Deprecated
    public static Bitmap bitmapThumbnail(String bitmapPath, int targetWidth, int targetHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);
        return BitmapFactory.decodeFile(bitmapPath, options);
    }

    /** 缩略图 */
    public static byte[] bitampThumbnail2Byte(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.JPEG, Config.THUMBNAIL_QUALITY_0_100, outputStream);
        return outputStream.toByteArray();
    }

}