package org.rmj.g3appdriver.Telemarketing;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.lib.Telemarketing.classobj.GTeleApp;
import org.rmj.g3appdriver.lib.Telemarketing.entities.ELeadCalls;
import org.rmj.g3appdriver.lib.authentication.GAuthentication;
import org.rmj.g3appdriver.lib.authentication.factory.Auth;
import org.rmj.g3appdriver.lib.authentication.factory.iAuthenticate;
import org.rmj.g3appdriver.lib.authentication.pojo.LoginCredentials;

@RunWith(AndroidJUnit4.class)
public class TestImportLeads {
    private Application instance;
    private GTeleApp poTeleapp;
    private iAuthenticate poAuth;
    private Boolean isSuccess;
    private String TAG = getClass().getSimpleName();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Before
    public void setUp() throws Exception{
        instance= ApplicationProvider.getApplicationContext();

        AppConfig.getInstance(instance).setProductID("gRider");

        poTeleapp= new GTeleApp(instance);

        poAuth = new GAuthentication(instance).initAppAuthentication().getInstance(Auth.AUTHENTICATE);
        int auth = poAuth.DoAction(new LoginCredentials("mikegarcia8748@gmail.com", "123456", "09270359402"));
        Log.d(TAG, String.valueOf(auth));
    }
    @Test
    public void testImport(){
        poTeleapp.ImportLeads("M001160024", "(cSubscrbr IN ('0', '1'))");
        Log.d(TAG, poTeleapp.getMessage());
    }
    @Test
    public void testGetImports(){
        ELeadCalls leadCalls = poTeleapp.GetLeadTrans("M00116000026");
        if (leadCalls != null){
            Log.d(TAG, "TRANSNOX: "+ leadCalls.getsTransNox());
            Log.d(TAG, "CLIENTID: "+ leadCalls.getsClientID());
            Log.d(TAG, "MOBILENO: "+ leadCalls.getsMobileNo());
        }else {
            Log.d(TAG, "ERROR: NO LEAD CALLS RETRIEVED");
        }
    }
}
