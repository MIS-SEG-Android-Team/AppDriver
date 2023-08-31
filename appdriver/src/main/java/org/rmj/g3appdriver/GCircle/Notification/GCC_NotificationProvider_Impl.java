package org.rmj.g3appdriver.GCircle.Notification;

import android.app.Application;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.lib.Notifications.RemoteMessageParser;
import org.rmj.g3appdriver.lib.Notifications.model.NMM_Factory;
import org.rmj.g3appdriver.lib.Notifications.model.NotificationFactory;


public class GCC_NotificationProvider_Impl implements NotificationFactory {
    private static final String TAG = "GCCNotificationRcr_Impl";

    private final Application instance;

    public GCC_NotificationProvider_Impl(Application instance) {
        this.instance = instance;
    }

    @Override
    public NMM_Factory getInstance(RemoteMessage remoteMessage) {
        String lsSysMon = new RemoteMessageParser(remoteMessage).getValueOf("msgmon");
        switch (lsSysMon){
            case "00000":
//                return new NMM_Regular(instance);
            case "00001":
//                return new NMM_TableUpdate(instance);
            case "00002":
//                return new NMM_MPOrderStatus(instance);
            case "00003":
//                return new NMM_Promotions(instance);
            case "00004":
//                return new NMM_Events(instance);
            case "00005":
//                return new NMM_MPQuestions(instance);
            case "00006":
//                return new NMM_MPReview(instance);
            case "00007":
//                return new NMM_CustomerService(instance);
            default:
//                return new NMM_Panalo(instance);
        }
    }
}
