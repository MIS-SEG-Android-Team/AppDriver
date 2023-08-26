package org.rmj.g3appdriver.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class TestConfig {
    private static final String TAG = "TestConfig";

    private final SharedPreferences poPref;
    private final SharedPreferences.Editor poEdit;

    private static final String CONFIG_NAME = "GGC_TestConfig";
    private final int PRIV_MODE = 0;

    private static final String TEST_CASE = "cTestStat";

    private static TestConfig poInstance;

    private TestConfig(Context context){
        poPref = context.getSharedPreferences(CONFIG_NAME, PRIV_MODE);
        poEdit = poPref.edit();
    }

    public static TestConfig getInstance(Context context){
        if(poInstance == null){
            poInstance = new TestConfig(context);
        }
        return poInstance;
    }

    public void setTestStatus(boolean isTestMode){
        poEdit.putBoolean(TEST_CASE, isTestMode);
        poEdit.commit();
    }

    public boolean isTestMode(){
        return poPref.getBoolean(TEST_CASE, false);
    }
}
