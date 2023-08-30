package org.rmj.g3appdriver.GCircle.Notification;

import android.app.Application;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.GCircle.room.Entities.ENotificationMaster;
import org.rmj.g3appdriver.lib.Notifications.NOTIFICATION_STATUS;
import org.rmj.g3appdriver.lib.Notifications.model.NotificationFactory;


public class GCCNotification_Impl implements NotificationFactory {
    private static final String TAG = "GCCNotificationRcr_Impl";

    private final Application instance;

    public GCCNotification_Impl(Application instance) {
        this.instance = instance;
    }

    @Override
    public String Save(RemoteMessage foVal) {
        return null;
    }

    @Override
    public ENotificationMaster SendResponse(String mesgID, NOTIFICATION_STATUS status) {
        return null;
    }

    @Override
    public boolean CreateNotification(String title, String message) {
        return false;
    }
}
