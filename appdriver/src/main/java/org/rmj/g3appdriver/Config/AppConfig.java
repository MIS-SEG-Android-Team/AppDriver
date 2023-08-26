package org.rmj.g3appdriver.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {
    private static final String TAG = "AppConfig";

    private final SharedPreferences poPref;
    private final SharedPreferences.Editor poEdit;

    private static final String CONFIG_NAME = "GGC_AppConfig";
    private final int PRIV_MODE = 0;

    private static final String PACKAGE_NAME = "cPackageN";
    private static final String IS_APP_FIRST_LAUNCH = "cFrstLnch";
    private static final String APP_PRODUCT_ID = "sProdctID";
    private static final String APP_FIREBASE_TOKEN = "sAppToken";
    private static final String APP_CLIENT_TOKEN = "sCltToken";
    private static final String APPLICATION_AGREEMENT = "cTnCAggrx";

    private static AppConfig poInstance;

    private AppConfig(Context context){
        poPref = context.getSharedPreferences(CONFIG_NAME, PRIV_MODE);
        poEdit = poPref.edit();
    }

    public static AppConfig getInstance(Context context){
        if(poInstance == null){
            poInstance = new AppConfig(context);
        }
        return poInstance;
    }

    public void setPackageName(String fsPackageName){
        poEdit.putString(PACKAGE_NAME, fsPackageName);
        poEdit.commit();
    }

    public String getPackageName(){
        return poPref.getString(PACKAGE_NAME, "");
    }

    public void setFirstLaunch(boolean isAppFirstLaunch){
        poEdit.putBoolean(IS_APP_FIRST_LAUNCH, isAppFirstLaunch);
        poEdit.commit();
    }

    public boolean isFirstLaunch(){
        return poPref.getBoolean(IS_APP_FIRST_LAUNCH, true);
    }

    public void setProductID(String fsProductID){
        poEdit.putString(APP_PRODUCT_ID, fsProductID);
        poEdit.commit();
    }

    public String getProductID(){
        return poPref.getString(APP_PRODUCT_ID, "");
    }

    public void setFirebaseToken(String fsFirbaseToken){
        poEdit.putString(APP_FIREBASE_TOKEN, fsFirbaseToken);
        poEdit.commit();
    }

    public String getFirebaseToken(){
        return poPref.getString(APP_FIREBASE_TOKEN, "");
    }

    public void setClientToken(String fsClientToken){
        poEdit.putString(APP_CLIENT_TOKEN, fsClientToken);
        poEdit.commit();
    }

    public String getClientToken(){
        return poPref.getString(APP_CLIENT_TOKEN, "");
    }

    public void setAppAgreement(boolean isAgree){
        poEdit.putBoolean(APPLICATION_AGREEMENT, isAgree);
        poEdit.commit();
    }

    public boolean hasAgreedTermsAndConditions(){
        return poPref.getBoolean(APPLICATION_AGREEMENT, false);
    }
}
