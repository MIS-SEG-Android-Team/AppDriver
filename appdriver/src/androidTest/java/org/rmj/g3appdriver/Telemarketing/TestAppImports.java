package org.rmj.g3appdriver.Telemarketing;

import static org.junit.Assert.assertTrue;

import android.app.Application;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Telemarketing.classobj.GTeleApp;

@RunWith(AndroidJUnit4.class)
public class TestAppImports {
    private Application instance;
    private String TAG = getClass().getSimpleName();
    private GTeleApp poTeleapp;
    private AppConfig loConfig;

    @Before
    public void setUp() throws Exception{
        instance= ApplicationProvider.getApplicationContext();
        loConfig = AppConfig.getInstance(instance);
        loConfig.setProductID("gRider");

        poTeleapp = new GTeleApp(instance);

        EmployeeMaster.UserAuthInfo loAuth = new EmployeeMaster.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011");
        assertTrue(loAuth.isAuthInfoValid());
    }
    @Test
    public void TestImportAppLeads(){
        poTeleapp.GetLeads("GAP0190004", "(cSubscrbr IN (0,1))");
        System.out.println(poTeleapp.getMessage());
    }
}
