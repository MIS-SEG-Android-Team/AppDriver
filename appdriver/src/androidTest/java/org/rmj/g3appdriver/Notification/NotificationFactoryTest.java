package org.rmj.g3appdriver.Notification;


import static org.junit.Assert.assertEquals;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.lib.Notifications.model.NotificationFactory;
import org.rmj.g3appdriver.lib.Notifications.model.NotificationFactoryProvider;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class NotificationFactoryTest {
    private static final String TAG = "NotificationFactoryTest";

    private Application instance;
    private NotificationFactory poSys;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void test01InitializeGCircle() {
        AppConfig.getInstance(instance).setProductID("gRider");
        poSys = NotificationFactoryProvider.getInstance(instance).initAppNotification();
        assertEquals("Guanzon Circle", poSys.getApplicationInstance());
    }

    @Test
    public void test02InitializeGConnect() {
        AppConfig.getInstance(instance).setProductID("GuanzonApp");
        poSys = NotificationFactoryProvider.getInstance(instance).initAppNotification();
        assertEquals("Guanzon Connect", poSys.getApplicationInstance());
    }
}
