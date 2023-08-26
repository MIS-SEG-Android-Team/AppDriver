package org.rmj.g3appdriver.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class AppServerConfig {
    private static final String TAG = "AppServerConfig";

    private final SharedPreferences poPref;
    private final SharedPreferences.Editor poEdit;

    private static final String CONFIG_NAME = "GGC_ServerConfig";
    private final int PRIV_MODE = 0;

    private static final String PRODUCTION_SERVER_ADDRESS = "sLiveSrvr";
    private static final String LOCAL_SERVER_ADDRESS = "sLocalSvr";

    private static AppServerConfig poInstance;

    private AppServerConfig(Context context){
        poPref = context.getSharedPreferences(CONFIG_NAME, PRIV_MODE);
        poEdit = poPref.edit();
    }



}
