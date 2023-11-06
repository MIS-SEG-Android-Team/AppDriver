package org.rmj.g3appdriver.dev.Http;

import android.app.Application;
import android.util.Log;

import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.dev.Http.Headers.GCircleHeaders;
import org.rmj.g3appdriver.dev.Http.Headers.GConnectHeaders;

public class HttpHeaderManager {
    private static final String TAG = "HttpHeaderManager";

    private static HttpHeaderManager poInstance;

    private final Application instance;

    private HttpHeaderManager(Application instance){
        this.instance = instance;
    }

    public static HttpHeaderManager getInstance(Application instance){
        if(poInstance == null){
            poInstance = new HttpHeaderManager(instance);
        }

        return poInstance;
    }

    public HttpHeaderProvider initializeHeader(){
        String lsProdctID = AppConfig.getInstance(instance).getProductID();
        switch (lsProdctID){
            case "gRider":
                Log.d(TAG, "Initialize headers for Guanzon Circle.");
                return GCircleHeaders.getInstance(instance);
            case "TeleMktg":
                Log.d(TAG, "Initialize headers for Telemarketing App.");
                return GCircleHeaders.getInstance(instance);
            case "GuanzonApp":
                Log.d(TAG, "Initialize headers for Guanzon App.");
                return GConnectHeaders.getInstance(instance);
            default:
                throw new IllegalArgumentException("ERROR: Product ID not properly initialize.");
        }
    }
}
