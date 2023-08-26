package org.rmj.g3appdriver.dev.Api;

import android.app.Application;

import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.Config.AppStatusConfig;
import org.rmj.g3appdriver.dev.Api.Providers.GCircleApiProvider;
import org.rmj.g3appdriver.dev.Api.Providers.GConnectApiProvider;
import org.rmj.g3appdriver.dev.Api.Providers.GTeleAppApiProvider;

public class APIManager {
    private static final String TAG = "APIManager";

    private final Application instance;

    private static APIManager poInstance;

    private APIManager(Application instance){
        this.instance = instance;
    }

    public static APIManager getInstance(Application instance){
        if(poInstance == null){
            poInstance = new APIManager(instance);
        }

        return poInstance;
    }

    public APIProvider initializeProvider(){
        String lsProdctID = AppConfig.getInstance(instance).getProductID();
        switch (lsProdctID){
            case "gRider":
                return new GCircleApiProvider();
            case "GuanzonApp":
                return new GConnectApiProvider();
            default:
                return new GTeleAppApiProvider();
        }
    }
}
