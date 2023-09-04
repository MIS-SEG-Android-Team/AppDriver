package org.rmj.g3appdriver.lib.Notifications.model;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.GCircle.room.Entities.ENotificationMaster;
import org.rmj.g3appdriver.lib.Notifications.NOTIFICATION_STATUS;

public interface NotificationFactory {

    NMM_Factory getInstance(RemoteMessage remoteMessage);

    String getApplicationInstance();
}
