package org.rmj.g3appdriver.lib.Notifications.Factory;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.lib.Notifications.data.Entity.ENotificationMaster;
import org.rmj.g3appdriver.lib.Notifications.NOTIFICATION_STATUS;

public interface NMM_Factory {

    String Save(RemoteMessage foVal);

    /**
     *
     * @param status parameters refer to what type of response must be send
     *            Receive, Read
     */
    ENotificationMaster SendResponse(String mesgID, NOTIFICATION_STATUS status);

    boolean CreateNotification(String title, String message);

    String getMessage();
}
