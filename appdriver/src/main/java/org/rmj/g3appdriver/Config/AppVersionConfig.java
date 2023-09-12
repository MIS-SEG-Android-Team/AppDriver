package org.rmj.g3appdriver.Config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppVersionConfig {
    private static final String TAG = "AppVersionConfig";

    private final SharedPreferences poPref;
    private final SharedPreferences.Editor poEdit;

    private static final String CONFIG_NAME = "GGC_AppConfig";
    private final int PRIV_MODE = 0;

    private static final String APP_VERSION_NAME = "sVrsnName";
    private static final String APP_VERSION_CODE = "sVrsnCode";

    private static AppVersionConfig poInstance;

    private AppVersionConfig(Context context){
        poPref = context.getSharedPreferences(CONFIG_NAME, PRIV_MODE);
        poEdit = poPref.edit();
    }

    public static AppVersionConfig getInstance(Context context){
        if(poInstance == null){
            poInstance = new AppVersionConfig(context);
        }
        return poInstance;
    }

    public void setVersionName(String fsName){
        poEdit.putString(APP_VERSION_NAME, fsName);
        poEdit.commit();
        Log.d(TAG, "Application version name has been set.");
    }

    public void setVersionCode(int fnCode){
        poEdit.putInt(APP_VERSION_CODE, fnCode);
        poEdit.commit();
        Log.d(TAG, "Application version code has been set.");
    }

    public String getVersionName(){
        return poPref.getString(APP_VERSION_NAME, "");
    }

    public int getVersionCode(){
        return poPref.getInt(APP_VERSION_CODE, 0);
    }
}
