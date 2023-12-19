package org.rmj.g3appdriver.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class DeviceConfig {
    private static final String TAG = "DeviceConfig";

    private final SharedPreferences poPref;
    private final SharedPreferences.Editor poEdit;

    private static final String CONFIG_NAME = "GGC_DeviceConfig";
    private final int PRIV_MODE = 0;

    private static final String DEVICE_ID = "sDeviceID";
    private static final String MOBILE_NO = "sMobileNo";

    private static DeviceConfig poInstance;

    private DeviceConfig(Context context){
        poPref = context.getSharedPreferences(CONFIG_NAME, PRIV_MODE);
        poEdit = poPref.edit();
    }

    public static DeviceConfig getInstance(Context context){
        if(poInstance == null){
            poInstance = new DeviceConfig(context);
        }
        return poInstance;
    }

    public void setDeviceID(String fsDeviceID){
        poEdit.putString(DEVICE_ID, fsDeviceID);
        poEdit.commit();
    }

    public String getDeviceID(){
        return poPref.getString(DEVICE_ID, "");
    }

    public void setMobileNO(String fsMobileNo){
        poEdit.putString(MOBILE_NO, fsMobileNo); //bug fixed wrong key "DEVICE_ID" instead of "MOBILE_NO"
        poEdit.commit();
    }

    public String getMobileNO(){
        return poPref.getString(MOBILE_NO, "");
    }
}
