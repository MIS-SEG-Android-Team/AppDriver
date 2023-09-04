package org.rmj.g3appdriver.lib.Notifications.Factory;

import com.google.firebase.messaging.RemoteMessage;

public interface NotificationFactory {

    NMM_Factory getInstance(RemoteMessage remoteMessage);

    String getApplicationInstance();
}
