package com.shengxiangui.tool;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.widget.PopupWindow;


import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shengxiangui.cn.ConstanceValue;
import com.shengxiangui.cn.Urls;
import com.shengxiangui.cn.config.AppResponse;
import com.shengxiangui.cn.config.callback.JsonCallback;
import com.shengxiangui.cn.model.PeiZhiBiaoModel;
import com.shengxiangui.mqtt.YingJianZhiLing;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shengxiangui.cn.Urls.SHENGXIANGUI_SHANGPIN;
import static com.shengxiangui.cn.config.JiBenXinXi.device_ccid;


public class Tools {

    /**
     * 生成二维码Bitmap
     *
     * @param context 上下文
     * @param data    文本内容
     * @param logoBm  二维码中心的Logo图标（可以为null）
     * @return 合成后的bitmap
     */
    public static Bitmap createQRImage(Context context, String data, Bitmap logoBm) {

        try {

            if (data == null || "".equals(data)) {
                return null;
            }

            int widthPix = ((Activity) context).getWindowManager().getDefaultDisplay()
                    .getWidth();
            widthPix = widthPix / 5 * 4;
            int heightPix = widthPix;

            //配置参数
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
            hints.put(EncodeHintType.MARGIN, 0); //default is 4

            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }

            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);

            if (logoBm != null) {
                bitmap = addLogo(bitmap, logoBm);
            }

            return bitmap;
            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            //return bitmap != null && bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 在二维码中间添加Logo图案
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }

    /**
     * @param urlpath
     * @return Bitmap
     * 根据图片url获取图片对象
     */
    public static Bitmap getBitMBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    public static Bitmap returnBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }


    public static void fitPopupWindowOverStatusBar(PopupWindow mPopupWindow, boolean needFullScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(needFullScreen);
                mLayoutInScreen.set(mPopupWindow, needFullScreen);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }



    /*
     * String 转化成图片
     */

    public static Bitmap converStringToIcon(String s) {
        try {
            Bitmap bitmap = null;
            byte[] b = Base64.decode(s, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            return bitmap;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 判断Activity是否Destroy
     * <p>
     * activity
     *
     * @return
     */
    public static boolean isDestroy(Activity mActivity) {
        if (mActivity == null || mActivity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }

    //播放广告  视频

    /**
     *         if (savedInstanceState == null) {
     *             supportFragmentManager
     *                 .beginTransaction()
     *                 .add(R.id.containerView, Video_BaseFragment.newInstance(Video.ORANGE_13.url))
     *                 .commit()
     *
     *         }
     */

    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     *
     * @param
     * @param list
     * @param len
     * @return
     */
    public static List splitList(List list, int len) {

        if (list == null || list.isEmpty() || len < 1) {
            return Collections.emptyList();
        }

        List result = new ArrayList<>();

        int size = list.size();
        int count = (size + len - 1) / len;

        for (int i = 0; i < count; i++) {
            List subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }

        return result;
    }


    /**
     * dp转px | px转dp
     */
    public static class DensityUtil {
        /**
         * 根据手机的分辨率从 dp(相对大小) 的单位 转成为 px(像素)
         */
        public static int dpToPx(Context context, float dpValue) {
            // 获取屏幕密度
            final float scale = context.getResources().getDisplayMetrics().density;
            // 结果+0.5是为了int取整时更接近
            return (int) (dpValue * scale + 0.5f);
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp(相对大小)
         */
        public static int pxToDp(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }
    }


    public static byte[] intToShort(int num) {
        // 以65535为例，转short = -1	-> 1111 1111 1111 1111
        // 右移8位，转byte				-> 0000 0000 1111 1111	-> 1111 1111
        // 转byte					-> 1111 1111
        short n = (short) num;
        byte[] b = new byte[2];
        b[0] = (byte) (n >> 8);
        b[1] = (byte) n;
        return b;
    }

    public static int shortToInt(byte b0, byte b1) {
        // 以65535[-1,-1]为例
        // b[0]左移八位 -> 1111 1111	-> 1111 1111 0000 0000
        // b[1] 位与0xff去除高位1		-> 0000 0000 1111 1111
        // 位或					-> 1111 1111 1111 1111
        // 位与去除高位1		-> 0000 0000 0000 0000 1111 1111 1111 1111
        return (short) ((b0 << 8) | (b1 & 0xff)) & 0xffff;
    }

    public static byte[] chuLiZiFu(String str) {
        String[] arr = str.split("\\.");
        byte[] bytes = new byte[2];


        bytes[0] = Byte.parseByte(arr[0]);
        bytes[1] = Byte.parseByte(arr[1]);
        return bytes;
    }


    public static void getShouJiKaHao(Context context) {

        getPhoneInfo(context);
        //getPeiZhiBiaoNet(context, imei);
    }

    public static String getPhoneInfo(Context context) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        StringBuffer sb = new StringBuffer();

        //   sb.append("\nLine1Number = " + tm.getLine1Number());

        sb.append("\nNetworkOperator = " + tm.getNetworkOperator());//移动运营商编号

        sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());//移动运营商名称

        sb.append("\nSimCountryIso = " + tm.getSimCountryIso());

        sb.append("\nSimOperator = " + tm.getSimOperator());

        sb.append("\nSimOperatorName = " + tm.getSimOperatorName());

        sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());

        sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());

        return sb.toString();

    }


    //请求柜门配置表
    private static void getPeiZhiBiaoNet(Context context, String sim_ccid) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "100059");
        map.put("key", Urls.key);
        map.put("device_ccid", device_ccid);
        map.put("sim_ccid", sim_ccid);
        Gson gson = new Gson();
        Log.e("map_data", gson.toJson(map));
        OkGo.<AppResponse<PeiZhiBiaoModel.DataBean>>post(SHENGXIANGUI_SHANGPIN)
                .tag(context)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<PeiZhiBiaoModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<PeiZhiBiaoModel.DataBean>> response) {

                    }

                    @Override
                    public void onError(Response<AppResponse<PeiZhiBiaoModel.DataBean>> response) {
                        super.onError(response);
                    }
                });
    }
}
