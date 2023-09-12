package org.rmj.g3appdriver.dev.Http;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.Config.AppConfig;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class HttpHeaderManagerTest {
    private static final String TAG = "HttpHeaderManagerTest";

    private Application instance;

    private HttpHeaderProvider poProvider;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        AppConfig.getInstance(instance).setProductID("gRider");
        poProvider = HttpHeaderManager.getInstance(instance).initializeHeader();
    }

    @Test
    public void test01GCircleHttpHeaderNullValidation() {
        AppConfig.getInstance(instance).setProductID("gRider");
        poProvider = HttpHeaderManager.getInstance(instance).initializeHeader();
        assertNotNull(poProvider);
    }

    @Test
    public void test02GConnectHttpHeaderNullValidation() {
        AppConfig.getInstance(instance).setProductID("GuanzonApp");
        poProvider = HttpHeaderManager.getInstance(instance).initializeHeader();
        assertNotNull(poProvider);
    }

    @Test
    public void test03CheckGCircleGetHeaders() {
        AppConfig.getInstance(instance).setProductID("gRider");
        poProvider = HttpHeaderManager.getInstance(instance).initializeHeader();
        assertNotNull(poProvider.getHeaders());
        Log.d(TAG, poProvider.getHeaders().toString());
    }

    @Test
    public void test04CheckGConnectGetHeaders() {
        AppConfig.getInstance(instance).setProductID("GuanzonApp");
        poProvider = HttpHeaderManager.getInstance(instance).initializeHeader();
        assertNotNull(poProvider.getHeaders());
        Log.d(TAG, poProvider.getHeaders().toString());
    }
}
