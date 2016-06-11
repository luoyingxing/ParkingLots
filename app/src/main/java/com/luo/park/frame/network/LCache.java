package com.luo.park.frame.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * LCache
 * <p/>
 * Created by luoyingxing on 2015/12/24.
 */
public class LCache {
    /**
     * LCache
     */
    private static String TAG = LCache.class.getSimpleName();
    /**
     * 默认的缓存地址
     */
    private static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tianrenenglish/image/";
    /**
     * 设置图片的宽高为原尺寸的 1/scale
     */
    private int scale = 1;
    /**
     * 首页轮播图
     */
    public static final String HOME_SLIDE_IMAGE = "home_slide_image";
    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * 图片的网络路径
     */
    private String imageUri = null;
    /**
     * 图片的名称
     */
    private String imageName = null;
    /**
     * 图片的模块名
     */
    private String moduleName = null;

    /**
     * 线程集合
     */
    private List<Thread> threadList;

    /**
     * 空的构造函数
     */
    LCache() {
    }

    /**
     * 构造函数
     *
     * @param context 上下文对象
     * @param module  图片的模块
     */
    public LCache(Context context, String module) {
        this.mContext = context;
        this.moduleName = module;
    }

    /**
     * setting the ImageUri
     *
     * @param imageUri 图片路径
     */
    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
        String[] string = imageUri.split("/");
        imageName = string[string.length - 1];
        Log.e(TAG, "模块[" + moduleName + "] 文件名[" + imageName + "]");
    }

    /**
     * get the File by imageName
     *
     * @return 图片文件
     */
    private File getFileUri() {
        return new File(CACHE_PATH + imageName);
    }

    /**
     * return the file path String
     *
     * @return 文件全路径
     */
    public String getFilePath() {
        return CACHE_PATH + imageName;
    }

    /**
     * save the image as file in sdcard
     */
    public void saveImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap mBitmap = BitmapFactory.decodeStream(getImageInputStream(imageUri));
                    saveAsFile(mBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * get the image's inputStream
     *
     * @param imageUrl 图片地址
     * @return 图片输入流
     */
    private InputStream getImageInputStream(String imageUrl) {
        URL url = null;
        try {
            url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return conn.getInputStream();
            } else {
                Log.e(TAG, "网络出错！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * save the iamge into sdcard
     *
     * @param bitmap 图片
     * @throws IOException 异常
     */
    private void saveAsFile(Bitmap bitmap) throws IOException {
        Log.e(TAG, CACHE_PATH);
        File dirFile = new File(CACHE_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        File imageFile = new File(CACHE_PATH + imageName);
        if (!imageFile.exists()) {
            imageFile.createNewFile();
        } else {
            imageFile.delete();
            imageFile.createNewFile();
        }
        FileOutputStream fot = new FileOutputStream(imageFile);
        BufferedOutputStream bos = new BufferedOutputStream(fot);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
    }

    /**
     * get the image from sdcard
     *
     * @return Bitmap  图片
     */
    public Bitmap getBitmapByUri() {
        ParcelFileDescriptor parcelFD = null;
        try {
            parcelFD = mContext.getContentResolver().openFileDescriptor(Uri.fromFile(getFileUri()), "r");
            FileDescriptor imageSource = parcelFD.getFileDescriptor();
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFileDescriptor(imageSource, null, o2);

        } catch (FileNotFoundException e) {
        } finally {
            if (parcelFD != null)
                try {
                    parcelFD.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    /**
     * get the value from prefer
     *
     * @param key 传入图片的id
     * @return 图片的网络地址
     */
    public String getValueFromPrefer(int key) {
        return mContext.getSharedPreferences("image", 0).getString(moduleName + key, "");
    }

    /**
     * save the calue into prefer
     *
     * @param key   传入图片的id
     * @param value 传入图片的网络地址
     */
    public void saveValueFromPrefer(int key, String value) {
        mContext.getSharedPreferences("image", 0).edit().putString(moduleName + key, value).commit();
    }

}
