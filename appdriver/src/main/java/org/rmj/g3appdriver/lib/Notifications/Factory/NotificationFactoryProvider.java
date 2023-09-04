package org.rmj.g3appdriver.lib.Notifications.Factory;

import android.app.Application;

import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.GCircle.Notification.GCC_NotificationFactoryImpl;
import org.rmj.g3appdriver.GConnect.Notification.GCN_NotificationFactoryImpl;

public class NotificationFactoryProvider {
    private static final String TAG = "NotificationFactoryProv";

    private final Application instance;

    private static NotificationFactoryProvider poInstance;

    private NotificationFactoryProvider(Application instance){
        this.instance = instance;
    }

    public static NotificationFactoryProvider getInstance(Application instance){
        if(poInstance == null){
            poInstance = new NotificationFactoryProvider(instance);
        }

        return poInstance;
    }

    public NotificationFactory initAppNotification(){
        String lsProdctID = AppConfig.getInstance(instance).getProductID();
        switch (lsProdctID){
            case "gRider":
                return new GCC_NotificationFactoryImpl(instance);
            case "GuanzonApp":
                return new GCN_NotificationFactoryImpl(instance);
            default:
                throw new IllegalArgumentException("Product ID not initialize properly.");
        }
    }
}
