package org.rmj.g3appdriver.Telemarketing;

import static org.junit.Assert.assertSame;

import android.app.Application;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClientMobile;
import org.rmj.g3appdriver.lib.Telemarketing.entities.ELeadCalls;

@RunWith(AndroidJUnit4.class)
public class TestRoomImports {
    private Application instance;
    private String TAG = getClass().getSimpleName();
    private GGC_GCircleDB TestDB;

    @Before
    public void setUp() throws Exception{
        instance= ApplicationProvider.getApplicationContext();
        TestDB = Room.inMemoryDatabaseBuilder(instance, GGC_GCircleDB.class).build();
    }

    @Test
    public void InsertUpdateToLeads(){
        ELeadCalls leadCalls = new ELeadCalls();
        leadCalls.setsTransNox("M0T123026687");
        leadCalls.setdTransact("2023-03-23 14:20:47");
        leadCalls.setsClientID("M01520000603");
        leadCalls.setsMobileNo("09481552529");
        leadCalls.setsRemarksx("");
        leadCalls.setsReferNox("M01520000237");
        leadCalls.setsSourceCD("MCSO");
        leadCalls.setsApprovCd("");
        leadCalls.setcTranStat("0");
        leadCalls.setsAgentIDx("");
        leadCalls.setdCallStrt(null);
        leadCalls.setdCallEndx(null);
        leadCalls.setnNoRetryx(0);
        leadCalls.setcSubscrbr("1");
        leadCalls.setcCallStat("0");
        leadCalls.setcTLMStatx("");
        leadCalls.setcSMSStatx("0");
        leadCalls.setnSMSSentx(0);
        leadCalls.setsModified("M001111122");
        leadCalls.setdModified("2023-03-23 14:20:47");

        assertSame(1, TestDB.teleLeadsDao().SaveLeads(leadCalls).intValue());
        assertSame(1, TestDB.teleLeadsDao().UpdateLeads(leadCalls));
    }

    @Test
    public void InsertUpdateToClients(){
        EClient2Call eClient2Call = new EClient2Call();
        eClient2Call.setsClientID("M01520000603");
        eClient2Call.setsClientNM("Enaje, Michael Acosta");
        eClient2Call.setxAddressx("# 80 Purok 8 Upper Pinget\\r\\n Baguio City Benguet 2600");
        eClient2Call.setsMobileNox("09481552529");
        eClient2Call.setsPhoneNox("");

        assertSame(1, TestDB.teleCallClientsDao().SaveClients(eClient2Call).intValue());
        assertSame(1, TestDB.teleCallClientsDao().UpdateClients(eClient2Call));
    }
    @Test
    public void InsertUpdateToClientsMobile(){
        EClientMobile eClientMobile = new EClientMobile();

        eClientMobile.setsClientID("M01520000603");
        eClientMobile.setnEntryNox(Integer.valueOf("0"));
        eClientMobile.setsMobileNo("09481552529");
        eClientMobile.setnPriority(Integer.valueOf("1"));
        eClientMobile.setcIncdMktg(null);
        eClientMobile.setnUnreachx(Integer.valueOf("0"));
        eClientMobile.setdLastVeri("2020-06-17");
        eClientMobile.setdInactive(null);
        eClientMobile.setnNoRetryx(Integer.valueOf("0"));
        eClientMobile.setcInvalidx("0");
        eClientMobile.setsIdleTime(null);
        eClientMobile.setcConfirmd("0");
        eClientMobile.setdConfirmd(null);
        eClientMobile.setcSubscr("1");
        eClientMobile.setdHoldMktg(null);
        eClientMobile.setdLastCall(null);
        eClientMobile.setcRecdStat("1");

        assertSame(1, TestDB.teleClientMobDao().SaveClientMobile(eClientMobile));
        assertSame(1, TestDB.teleClientMobDao().UpdateClientMobile(eClientMobile));
        assertSame(1, TestDB.teleClientMobDao().UpdateCallTrans("M01520000603", "09481552529",
                "2023-11-09", 0));
    }
}
