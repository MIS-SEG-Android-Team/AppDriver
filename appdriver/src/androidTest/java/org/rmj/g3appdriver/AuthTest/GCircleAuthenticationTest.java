package org.rmj.g3appdriver.AuthTest;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.Config.AppStatusConfig;
import org.rmj.g3appdriver.lib.account.GAuthentication;
import org.rmj.g3appdriver.lib.account.factory.Auth;
import org.rmj.g3appdriver.lib.account.factory.iAuthenticate;
import org.rmj.g3appdriver.lib.account.pojo.LoginCredentials;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class GCircleAuthenticationTest {
    private static final String TAG = GCircleAuthenticationTest.class.getSimpleName();

    private Application instance;

    private GAuthentication poAccount;
    private iAuthenticate poSys;

    private boolean isSuccess = false;
    private String message;

    @Before
    public void setUp() throws Exception {
        this.instance = ApplicationProvider.getApplicationContext();
        AppConfig.getInstance(instance).setProductID("gRider");
        AppStatusConfig.getInstance(instance).setTestStatus(true);
        this.poAccount = new GAuthentication(instance);
        this.poSys = poAccount.initAppAuthentication().getInstance(Auth.AUTHENTICATE);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test01LoginAccount() {
        LoginCredentials loInfo = new LoginCredentials("mikegarcia8748@gmail.com", "123456", "09171870011");
        int lnResult = poSys.DoAction(loInfo);
        if(lnResult == 1){
            isSuccess = true;
        } else if(lnResult == 2){
            Log.e(TAG, poSys.getMessage());
        } else {
            Log.e(TAG, poSys.getMessage());
        }
        assertTrue(isSuccess);
    }
}