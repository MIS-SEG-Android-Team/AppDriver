package org.rmj.g3appdriver.GCircle.Apps.TeleMarketing;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

public class CallInteractManager {
    private static final String TAG = "CallInteractManager";

    private final Application instance;

    private String message;

    public CallInteractManager(Application instance) {
        this.instance = instance;
    }

    public CallInteractManager getNextCall(){
        try{

        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
        }
    }
}
