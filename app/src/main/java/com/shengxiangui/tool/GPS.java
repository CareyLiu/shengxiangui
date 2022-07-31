package com.shengxiangui.tool;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shengxiangui.cn.Urls;
import com.shengxiangui.cn.config.AppResponse;
import com.shengxiangui.cn.config.callback.JsonCallback;
import com.shengxiangui.cn.model.GouMaiWuPinModel;
import com.shengxiangui.cn.model.HuoHaoAndZhongLiangModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shengxiangui.cn.Urls.SHENGXIANGUI;
import static com.shengxiangui.cn.config.JiBenXinXi.device_ccid;


public class GPS {
    private Context context;
    private LocationManager locationManager;
    private static final String TAG = "GPS-Info";

    public GPS(Context context) {
        this.context = context;
        initLocationManager();
    }

    /**
     * 获取权限，并检查有无开户GPS
     */
    private void initLocationManager() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            // 转到手机设置界面，用户设置GPS
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        getProviders();
    }

    /**
     * 获取可定位的方式
     */
    private MyLocationListener myLocationListener;
    private String bestProvider;

    private void getProviders() {
        //获取定位方式
        List<String> providers = locationManager.getProviders(true);
        for (String s : providers) {
            Log.e(TAG, s);
        }

        Criteria criteria = new Criteria();
        // 查询精度：高，Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精确
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 是否查询海拨：否
        criteria.setAltitudeRequired(true);
        // 是否查询方位角 : 否
        criteria.setBearingRequired(false);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 电量要求：低
        // criteria.setPowerRequirement(Criteria.ACCURACY_LOW);
        bestProvider = locationManager.getBestProvider(criteria, false);  //获取最佳定位
        myLocationListener = new MyLocationListener();

    }

    public void startLocation() {
        Log.e(TAG, "startLocation: ");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(bestProvider, 0, 0, myLocationListener);

    }

    public void stopLocation() {
        Log.e(TAG, "stopLocation: ");
        locationManager.removeUpdates(myLocationListener);

    }

    /**
     * @param x 纬度
     * @param y 精度
     */
    private void getNet(String x, String y) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "100060");
        map.put("key", Urls.key);
        map.put("device_ccid", device_ccid);
        map.put("x", x);//坐标x
        map.put("y", x);//坐标y

        Gson gson = new Gson();
        Log.e("map_data", gson.toJson(map));
        OkGo.<AppResponse<GouMaiWuPinModel.DataBean>>post(SHENGXIANGUI)
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<GouMaiWuPinModel.DataBean>>() {
                    @Override
                    public void onSuccess(Response<AppResponse<GouMaiWuPinModel.DataBean>> response) {


                    }
                });
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            //定位时调用
            Log.e(TAG, "onLocationChanged");

            List<Address> addresses = new ArrayList<>();
            //经纬度转城市
            Geocoder geocoder = new Geocoder(context);
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);


            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Address address : addresses) {
                //国家  CN
                Log.e(TAG, address.getCountryCode());
                //国家
                Log.e(TAG, address.getCountryName());
                //省，市，地址
                Log.e(TAG, address.getAdminArea());
                Log.e(TAG, address.getLocality());
                Log.e(TAG, address.getFeatureName());

                //经纬度
                Log.e(TAG, String.valueOf(address.getLatitude()));
                Log.e(TAG, String.valueOf(address.getLongitude()));
//                Log.e(TAG,address.getAddressLine());
                getNet(address.getLatitude()+"",address.getLongitude()+"");
                stopLocation();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //定位状态改变
            Log.e(TAG, "onStatusChanged");

        }

        @Override
        public void onProviderEnabled(String provider) {
            //定位开启
            Log.e(TAG, "onProviderEnabled");

        }

        @Override
        public void onProviderDisabled(String provider) {
            //定位关闭
            Log.e(TAG, "onProviderDisabled");

        }
    }

}


