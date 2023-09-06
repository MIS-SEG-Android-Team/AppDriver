package org.rmj.g3appdriver.lib.authentication;

import android.app.Application;
import android.util.Log;

import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.lib.authentication.factory.iAuthentication;
import org.rmj.g3appdriver.GCircle.Authentication.GCircleAccount_Impl;
import org.rmj.g3appdriver.GConnect.Authentication.gConnectAuth;

public class GAuthentication {
    private static final String TAG = GAuthentication.class.getSimpleName();

    private final Application instance;
    private final AppConfig poConfig;

    public GAuthentication(Application instance) {
        this.instance = instance;
        this.poConfig = AppConfig.getInstance(instance);
    }

    public iAuthentication initAppAuthentication(){
        String lsProdctID = poConfig.getProductID();
        if ("gRider".equals(lsProdctID)) {
            Log.d(TAG, "Initialize employee authentication");
            return new GCircleAccount_Impl(instance);
        }
        Log.d(TAG, "Initialize client account");
        return new gConnectAuth(instance);
    }
}
