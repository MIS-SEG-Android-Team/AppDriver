package org.rmj.g3appdriver.GConnect.Notification;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.GCircle.room.Entities.ENotificationMaster;
import org.rmj.g3appdriver.lib.Notifications.NOTIFICATION_STATUS;
import org.rmj.g3appdriver.lib.Notifications.model.NMM_Factory;
import org.rmj.g3appdriver.lib.Notifications.model.NotificationFactory;
import org.rmj.g3appdriver.lib.Notifications.model.iNotification;
import org.rmj.g3appdriver.lib.Notifications.pojo.NotificationItemList;

import java.util.List;

public class GCTNotification_Impl implements NotificationFactory {
    private static final String TAG = "GCTNotification_Impl";

    private final Application instance;

    public GCTNotification_Impl(Application instance) {
        this.instance = instance;
    }

    @Override
    public NMM_Factory getInstance(RemoteMessage remoteMessage) {
        return null;
    }
}
