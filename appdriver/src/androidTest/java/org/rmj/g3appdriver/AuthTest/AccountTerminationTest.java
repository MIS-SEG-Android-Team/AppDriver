package org.rmj.g3appdriver.AuthTest;


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
import org.rmj.g3appdriver.Config.AppStatusConfig;
import org.rmj.g3appdriver.lib.authentication.GAuthentication;
import org.rmj.g3appdriver.lib.authentication.factory.Auth;
import org.rmj.g3appdriver.lib.authentication.factory.iAuthenticate;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class AccountTerminationTest {
    private static final String TAG = AccountTerminationTest.class.getSimpleName();

    private Application instance;

    private GAuthentication poAccount;
    private iAuthenticate poSys;

    private boolean isSuccess = false;
    private String message;

    @Before
    public void setUp() throws Exception {
        this.instance = ApplicationProvider.getApplicationContext();
        AppConfig.getInstance(instance).setProductID("gRider");
        AppStatusConfig.getInstance(instance).setTestStatus(false);
        this.poAccount = new GAuthentication(instance);
        this.poSys = poAccount.initAppAuthentication().getInstance(Auth.DEACTIVATE);
    }

    @Test
    public void test01TerminateAccount() {
        String lsPassword = "123456";

        int lnResult = poSys.DoAction(lsPassword);

        if(lnResult == 1){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }
        assertTrue(isSuccess);
    }
}
