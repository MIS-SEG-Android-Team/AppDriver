package org.rmj.g3appdriver.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class AppVersionConfig {
    private static final String TAG = "AppVersionConfig";

    private final SharedPreferences poPref;
    private final SharedPreferences.Editor poEdit;

    private static final String CONFIG_NAME = "GGC_AppConfig";
    private final int PRIV_MODE = 0;

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

}
