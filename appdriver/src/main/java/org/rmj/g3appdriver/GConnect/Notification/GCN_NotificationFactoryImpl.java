package org.rmj.g3appdriver.GConnect.Notification;

import android.app.Application;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.GConnect.Notification.NMM.GCN_CustomerSrvcNMMFactoryImpl;
import org.rmj.g3appdriver.GConnect.Notification.NMM.GCN_EventsNMMFactoryImpl;
import org.rmj.g3appdriver.GConnect.Notification.NMM.GCN_MPInquiryNMMFactoryImpl;
import org.rmj.g3appdriver.GConnect.Notification.NMM.GCN_MPOrderNMMFactoryImpl;
import org.rmj.g3appdriver.GConnect.Notification.NMM.GCN_MPReviewNMMFactoryImpl;
import org.rmj.g3appdriver.GConnect.Notification.NMM.GCN_PanaloNMMFactoryImpl;
import org.rmj.g3appdriver.GConnect.Notification.NMM.GCN_PromoNMMFactoryImpl;
import org.rmj.g3appdriver.GConnect.Notification.NMM.GCN_RegularNMMFactoryImpl;
import org.rmj.g3appdriver.GConnect.Notification.NMM.GCN_TableUpdateNMMFactoryImpl;
import org.rmj.g3appdriver.lib.Notifications.Obj.Receiver.NMM_CustomerService;
import org.rmj.g3appdriver.lib.Notifications.Obj.Receiver.NMM_Events;
import org.rmj.g3appdriver.lib.Notifications.Obj.Receiver.NMM_MPOrderStatus;
import org.rmj.g3appdriver.lib.Notifications.Obj.Receiver.NMM_MPQuestions;
import org.rmj.g3appdriver.lib.Notifications.Obj.Receiver.NMM_MPReview;
import org.rmj.g3appdriver.lib.Notifications.Obj.Receiver.NMM_Panalo;
import org.rmj.g3appdriver.lib.Notifications.Obj.Receiver.NMM_Promotions;
import org.rmj.g3appdriver.lib.Notifications.Obj.Receiver.NMM_Regular;
import org.rmj.g3appdriver.lib.Notifications.Obj.Receiver.NMM_TableUpdate;
import org.rmj.g3appdriver.lib.Notifications.RemoteMessageParser;
import org.rmj.g3appdriver.lib.Notifications.model.NMM_Factory;
import org.rmj.g3appdriver.lib.Notifications.model.NotificationFactory;

public class GCN_NotificationFactoryImpl implements NotificationFactory {
    private static final String TAG = "GCTNotification_Impl";

    private final Application instance;

    public GCN_NotificationFactoryImpl(Application instance) {
        this.instance = instance;
    }

    @Override
    public NMM_Factory getInstance(RemoteMessage remoteMessage) {
        String lsSysMon = new RemoteMessageParser(remoteMessage).getValueOf("msgmon");
        switch (lsSysMon){
            case "00000":
                return new GCN_RegularNMMFactoryImpl(instance);
            case "00001":
                return new GCN_TableUpdateNMMFactoryImpl(instance);
            case "00002":
                return new GCN_MPOrderNMMFactoryImpl(instance);
            case "00003":
                return new GCN_PromoNMMFactoryImpl(instance);
            case "00004":
                return new GCN_EventsNMMFactoryImpl(instance);
            case "00005":
                return new GCN_MPInquiryNMMFactoryImpl(instance);
            case "00006":
                return new GCN_MPReviewNMMFactoryImpl(instance);
            case "00007":
                return new GCN_CustomerSrvcNMMFactoryImpl(instance);
            default:
                return new GCN_PanaloNMMFactoryImpl(instance);
        }
    }

    @Override
    public String getApplicationInstance() {
        return "Guanzon Connect";
    }
}
