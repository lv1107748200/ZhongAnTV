package com.hr.zhongantv.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.DisplayMetrics;

import com.hr.zhongantv.R;
import com.hr.zhongantv.base.BaseActivity;
import com.hr.zhongantv.utils.NLog;
import com.hr.zhongantv.widget.single.ClientInfo;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 吕 on 2018/3/13.
 */

public class SplashActiity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        super.init();

        //setData();
       // getV();

//        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//        NLog.e(NLog.TAGOther, "androidId--->"+androidId);
//        NLog.e(NLog.TAGOther, "androidId--->"+androidId.toUpperCase());

      //  ClientInfo.getInstance().setToken(androidId.toUpperCase());

        Intent intent = new Intent();
        intent.setClass(this,VideoActivity.class);
        startActivity(intent);
        finish();
    }


    private void getV(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
// 屏幕宽度（像素）
        int width = metric.widthPixels;
// 屏幕高度（像素）
        int height = metric.heightPixels;
// 屏幕密度（1.0 / 1.5 / 2.0）
        float density = metric.density;
// 屏幕密度DPI（160 / 240 / 320）
        int densityDpi = metric.densityDpi;
        String info = "机顶盒型号: " + android.os.Build.MODEL
                + ",\nSDK版本:" + android.os.Build.VERSION.SDK
                + ",\n系统版本:" + android.os.Build.VERSION.RELEASE
                + "\n屏幕宽度（像素）: "+width
                + "\n屏幕高度（像素）: " + height
                + "\n屏幕密度 : "+density
                +"\n屏幕密度DPI: "+densityDpi;
        NLog.e(NLog.TAGOther, info);
    }

    //唯一标识
    private void setData(){

        String serialnum = null;

        try {

            Class<?> c = Class.forName("android.os.SystemProperties");

            Method get = c.getMethod("get", String.class, String.class );

            serialnum = (String)(   get.invoke(c, "ro.serialno", "unknown" )  );

        } catch (Exception ignored) {

        }
        NLog.e(NLog.TAGOther, "serialnum--->"+serialnum);

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        NLog.e(NLog.TAGOther, "androidId--->"+androidId);

        WifiManager wm = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
        NLog.e(NLog.TAGOther, "m_szWLANMAC--->"+m_szWLANMAC);



        //String m_szLongID = serialnum + androidId + m_szWLANMAC;
        String m_szLongID = m_szWLANMAC;

// compute md5
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(),0,m_szLongID.length());
// get md5 bytes
        byte p_md5Data[] = m.digest();
// create a hex string
        String m_szUniqueID = new String();
        for (int i=0;i<p_md5Data.length;i++) {
            int b =  (0xFF & p_md5Data[i]);
// if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF)
                m_szUniqueID+="0";
// add number to string
            m_szUniqueID+=Integer.toHexString(b);
        }   // hex string to uppercase
        m_szUniqueID = m_szUniqueID.toUpperCase();

       // ClientInfo.getInstance().setToken(androidId.toUpperCase());

        NLog.e(NLog.TAGOther, "m_szUniqueID--->"+m_szUniqueID);

    }
}
