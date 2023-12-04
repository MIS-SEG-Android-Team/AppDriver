package org.rmj.g3appdriver.Telemarketing;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.Config.AppStatusConfig;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.TeleMarketing.CallInteractManager;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOLeadCalls;

@RunWith(AndroidJUnit4.class)
public class TestAppImports {
    private Application instance;
    private String TAG = getClass().getSimpleName();
    private EmployeeMaster.UserAuthInfo loAuth;
    private EmployeeMaster poUser;
    private CallInteractManager poCallManager;
    private AppConfig loConfig;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception{
        instance= ApplicationProvider.getApplicationContext();
        loConfig = AppConfig.getInstance(instance);
        loConfig.setProductID("gRider");

        AppStatusConfig.getInstance(instance).setTestStatus(true); //use this to test api on device local ip

        poUser = new EmployeeMaster(instance);
        poCallManager = new CallInteractManager(instance);

        //TO RUN THIS LOGIN SUCCESSFULLY, WIPE EMULATOR DATA PER DAY TO CREATE NEW SESSION
        loAuth = new EmployeeMaster.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011");
        //assertTrue(poUser.AuthenticateUser(loAuth));
    }
    //SEE GTeleConstants TO ADD SIM CARD IF READING SIM CARD FAILED
    @Test
    public void GetSubscrClause(){
        String SQLCondition = poCallManager.CreateSubscrCondition();
        assertNotNull(SQLCondition);
        System.out.println(SQLCondition);
    }
    /*IF ERROR OCCURS, TRY TO RUN ONLY TEST YOU NEED AND COMMENT OTHER TESTS.*/
    @Test
    public void ImportCalls(){
        Boolean isImported = poCallManager.ImportCalls();
        assertTrue(isImported);
    }
    /*@Test
    public void ImportClient() throws JSONException {
        Boolean isImported = poCallManager.SaveClient2Call();
        assertTrue(isImported);
        System.out.println(poCallManager.getMessage());
    }
    @Test
    public void ImportClientMobile() throws JSONException {
        Boolean isImported = poCallManager.SaveClientMobile();
        assertTrue(isImported);
        System.out.println(poCallManager.getMessage());
    }
    @Test
    public void ImportInquiries() throws JSONException {
        Boolean isImported = poCallManager.SaveInquiries();
        assertTrue(isImported);
        System.out.println(poCallManager.getMessage());
    }*/
}
