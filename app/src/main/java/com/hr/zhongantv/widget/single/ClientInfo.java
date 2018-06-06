package com.hr.zhongantv.widget.single;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import com.hr.zhongantv.base.BaseApplation;
import com.hr.zhongantv.utils.CheckUtil;
import com.hr.zhongantv.utils.NLog;

/**
 * Created by 吕 on 2018/3/28.
 */

public class ClientInfo {

    volatile private static ClientInfo instance = null;

    private String token;
    private String URL;


    public static ClientInfo getInstance(){
        if(instance == null){
            synchronized (ClientInfo.class) {
                if(instance == null){
                    instance = new ClientInfo();
                }
            }
        }

        return instance;
    }

    public String getToken(){

        if(CheckUtil.isEmpty(token)){

            String androidId = Settings.Secure.getString(BaseApplation.getBaseApp().getContentResolver(), Settings.Secure.ANDROID_ID);
            NLog.e(NLog.TAGOther, "androidId--->"+androidId);
            NLog.e(NLog.TAGOther, "androidId--->"+androidId.toUpperCase());

            WifiManager wm = (WifiManager)BaseApplation.getBaseApp().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
            NLog.e(NLog.TAGOther, "m_szWLANMAC--->"+m_szWLANMAC);

           // token = "E3B935C54B3838D1";
            token = androidId.toUpperCase();
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void clearUrl(){
        URL = null;
    }

    public void clear(){
        token = null;
    }

}
