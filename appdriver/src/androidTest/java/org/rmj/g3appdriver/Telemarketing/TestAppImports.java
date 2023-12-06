package org.rmj.g3appdriver.Telemarketing;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.Config.AppStatusConfig;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.TeleMarketing.CallInteractManager;
import org.rmj.g3appdriver.GCircle.Apps.TeleMarketing.LeadsInformation;

@RunWith(AndroidJUnit4.class)
public class TestAppImports {
    private Application instance;
    private String TAG = getClass().getSimpleName();
    private EmployeeMaster.UserAuthInfo loAuth;
    private EmployeeMaster poUser;
    private CallInteractManager poCallManager;
    private AppConfig loConfig;

    public LeadsInformation GetQueues(){
        LeadsInformation loLeads = new LeadsInformation();
        loLeads.setsClientID("M01520000603");
        loLeads.setsMobileNo("09481552529");
        loLeads.setsTransNox("M0T123072843");
        loLeads.setsReferNox("M0T123000816");

        loLeads.setsSourceCD("INQR");
        loLeads.setsSubscr("0");

        return loLeads;
    }
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
        poCallManager.InitTransaction(GetQueues());

        //WIPE DATA AND RUN TestLoginAccount BEFORE RUNNING THIS TEST, FOR VALID LOGIN AND SUCCESS
        //"INVALID LOG / INVALID AUTH DETECTED" - WIPE EMULATOR DATA
        //"INVALID MOBILE NUMBER DETECTED" - CHANGE lsMobileN VALUE TO SPECIFIC NUMBER
        loAuth = new EmployeeMaster.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011");
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
        System.out.println(poCallManager.getMessage());
        assertTrue(isImported);
    }
    @Test
    public void ImportClient() throws JSONException {
        Boolean isImported = poCallManager.SaveClient2Call();
        System.out.println(poCallManager.getMessage());
        assertTrue(isImported);
    }
    @Test
    public void ImportClientMobile() throws JSONException {
        Boolean isImported = poCallManager.SaveClientMobile();
        System.out.println(poCallManager.getMessage());
        assertTrue(isImported);
    }
    @Test
    public void ImportInquiries() throws JSONException {
        Boolean isImported = poCallManager.SaveInquiries();
        System.out.println(poCallManager.getMessage());
        assertTrue(isImported);
    }
    @Test
    public void SaveCallStatus(){
        Boolean isSaved = poCallManager.SaveCallStatus("CANNOT BE REACHED", "C001120623021");
        System.out.println(poCallManager.getMessage());
        assertTrue(isSaved);
    }
    @Test
    public void SaveSchedule(){
        Boolean isSaved = poCallManager.SaveSchedule("2023-12-06", "0", "RESCHEDULE CLIENT IS BUSY AGAIN");
        System.out.println(poCallManager.getMessage());
        assertTrue(isSaved);
    }
}
