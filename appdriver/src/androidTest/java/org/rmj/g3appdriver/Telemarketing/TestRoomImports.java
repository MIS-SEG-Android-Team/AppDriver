package org.rmj.g3appdriver.Telemarketing;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import android.app.Application;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOClientMobile;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOLeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOMCInquiry;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClientMobile;
import org.rmj.g3appdriver.lib.Telemarketing.entities.ELeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EMCInquiry;

@RunWith(AndroidJUnit4.class)
public class TestRoomImports {
    private Application instance;
    private String TAG = getClass().getSimpleName();
    private GGC_GCircleDB TestDB;
    private DAOLeadCalls daoLeadCalls;
    private DAOClient2Call daoClient2Call;
    private DAOClientMobile daoClientMobile;
    private DAOMCInquiry daomcInquiry;

    @Before
    public void setUp() throws Exception{
        instance= ApplicationProvider.getApplicationContext();
        TestDB = Room.inMemoryDatabaseBuilder(instance, GGC_GCircleDB.class).build();

        daoLeadCalls = TestDB.teleLeadsDao();
        daoClient2Call = TestDB.teleCallClientsDao();
        daoClientMobile = TestDB.teleClientMobDao();
        daomcInquiry = TestDB.teleMCInquiryDao();
    }
    public ELeadCalls GetLeadCalls(){
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

        return leadCalls;
    }
    public EClient2Call GetClient2Call(){
        EClient2Call eClient2Call = new EClient2Call();
        eClient2Call.setsClientID("M01520000603");
        eClient2Call.setsClientNM("Enaje, Michael Acosta");
        eClient2Call.setxAddressx("# 80 Purok 8 Upper Pinget\\r\\n Baguio City Benguet 2600");
        eClient2Call.setsMobileNox("09481552529");
        eClient2Call.setsPhoneNox("");

        return eClient2Call;
    }
    public EClientMobile GetClientMobile(){
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

        return eClientMobile;
    }
    public EMCInquiry GetMCInquiry(){
        EMCInquiry emcInquiry = new EMCInquiry();

        emcInquiry.setsTransNox("M0T123000816");
        emcInquiry.setdFollowUp("2023-11-21 00:00:00");
        emcInquiry.setsClientID("M16323000812");
        emcInquiry.setsBrandIDx("M0W1003");
        emcInquiry.setsModelIDx("M00122101");
        emcInquiry.setsColorIDx("");
        emcInquiry.setnTerms(0);
        emcInquiry.setdTargetxx("2023-09-05");
        emcInquiry.setnDownPaym(0.00);
        emcInquiry.setnMonAmort(0.00);
        emcInquiry.setnCashPrc(129900.00);
        emcInquiry.setsRelatnID(null);
        emcInquiry.setsTableNM("MC_Product_Inquiry");

        return emcInquiry;
    }

    @Test
    public void RoomHasDao(){
        assertNotNull(GetLeadCalls());
        assertNotNull(GetClient2Call());
        assertNotNull(GetClientMobile());
        assertNotNull(GetMCInquiry());
    }

    @Test
    public void SaveLeads(){
        assertSame(1, daoLeadCalls.SaveLeads(GetLeadCalls()).intValue());
    }
   @Test
    public void UpdateLeads(){
        SaveLeads(); //import data first to update existing data
        assertSame(1, daoLeadCalls.UpdateLeads(GetLeadCalls()));
    }
    @Test
    public void SaveClient2Call(){
        assertSame(1, daoClient2Call.SaveClients(GetClient2Call()).intValue());
    }
    @Test
    public void UpdateClient2Call(){
        SaveClient2Call(); //import data first to update existing data
        assertSame(1, daoClient2Call.UpdateClients(GetClient2Call()));
    }
    @Test
    public void SaveClientMobile(){
        assertSame(1, daoClientMobile.SaveClientMobile(GetClientMobile()).intValue());
    }
    @Test
    public void UpdateClientMobile(){
        SaveClientMobile(); //import data first to update existing data
        assertSame(1, daoClientMobile.UpdateClientMobile(GetClientMobile()));
    }
    @Test
    public void UpdateCallTrans(){
        SaveClientMobile(); //import data first to update existing data
        assertSame(1, daoClientMobile.UpdateCallTrans("M01520000603", "09481552529",
                "2023-11-09", 0));
    }
    @Test
    public void SaveMCInquiry(){
        assertSame(1, daomcInquiry.SaveMCInq(GetMCInquiry()).intValue());
    }
    @Test
    public void UpdateMCInquiry(){
        SaveMCInquiry(); //import data first to update existing data
        assertSame(1, daomcInquiry.UpdateMCInq(GetMCInquiry()));
    }
}
