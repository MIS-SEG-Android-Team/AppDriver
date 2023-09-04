package org.rmj.g3appdriver.GCircle.Notification;

import android.app.Application;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.GCircle.Notification.NMM.GCC_EventNMMFactoryImpl;
import org.rmj.g3appdriver.GCircle.Notification.NMM.GCC_PanaloNMMFactoryImpl;
import org.rmj.g3appdriver.GCircle.Notification.NMM.GCC_PromoNMMFactoryImpl;
import org.rmj.g3appdriver.GCircle.Notification.NMM.GCC_RegularNMMFactoryImpl;
import org.rmj.g3appdriver.GCircle.Notification.NMM.GCC_TableUpdateNMMFactoryImpl;
import org.rmj.g3appdriver.lib.Notifications.RemoteMessageParser;
import org.rmj.g3appdriver.lib.Notifications.model.NMM_Factory;
import org.rmj.g3appdriver.lib.Notifications.model.NotificationFactory;


public class GCC_NotificationFactoryImpl implements NotificationFactory {
    private static final String TAG = "GCCNotificationRcr_Impl";

    private final Application instance;

    public GCC_NotificationFactoryImpl(Application instance) {
        this.instance = instance;
    }

    @Override
    public NMM_Factory getInstance(RemoteMessage remoteMessage) {
        String lsSysMon = new RemoteMessageParser(remoteMessage).getValueOf("msgmon");
        switch (lsSysMon){
            case "00000":
                return new GCC_RegularNMMFactoryImpl(instance);
            case "00001":
                return new GCC_TableUpdateNMMFactoryImpl(instance);
            case "00002":
            case "00007":
            case "00006":
            case "00005":
                throw new IllegalArgumentException("This notification type is not suitable for Guanzon Circle.");
            case "00003":
                return new GCC_PromoNMMFactoryImpl(instance);
            case "00004":
                return new GCC_EventNMMFactoryImpl(instance);
            default:
                return new GCC_PanaloNMMFactoryImpl(instance);
        }
    }

    @Override
    public String getApplicationInstance() {
        return "Guanzon Circle";
    }
}
