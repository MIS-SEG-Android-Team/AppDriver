package org.rmj.g3appdriver.GCircle.Apps.TeleMarketing;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LiveData;
import org.rmj.g3appdriver.GCircle.Account.EmployeeSession;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Telemarketing.classobj.GSimSubscriber;
import org.rmj.g3appdriver.lib.Telemarketing.classobj.GTeleApp;
import org.rmj.g3appdriver.lib.Telemarketing.constants.GTeleConstants;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOLeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOMCInquiry;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.entities.ELeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EMCInquiry;

import java.util.List;

public class CallInteractManager {
    private static final String TAG = "CallInteractManager";
    private Context context;
    private GTeleApp poTeleApp;
    private EmployeeSession poSession;
    private DAOLeadCalls poDaoLeadCalls;
    private DAOClient2Call poDaoClient2Call;
    private DAOMCInquiry poDaomcInquiry;
    private GTeleConstants loConstants;
    private GSimSubscriber loSubscriber;
    private String message;
    public CallInteractManager(Application instance) {
        this.context = instance.getApplicationContext();

        this.poTeleApp = new GTeleApp(instance);
        this.poSession = EmployeeSession.getInstance(instance);
        this.poDaoLeadCalls = GGC_GCircleDB.getInstance(instance.getApplicationContext()).teleLeadsDao();
        this.poDaoClient2Call = GGC_GCircleDB.getInstance(instance.getApplicationContext()).teleCallClientsDao();
        this.poDaomcInquiry = GGC_GCircleDB.getInstance(instance.getApplicationContext()).teleMCInquiryDao();
        this.loSubscriber = new GSimSubscriber(instance.getApplicationContext());
        this.loConstants = new GTeleConstants();
    }
    public String getMessage(){
        return message;
    }
    public void Init(){
        String sUserID = poSession.getUserID();

        //IMPORT LEADS WITH CLIENT INFO
        if (poTeleApp.ImportLeads(sUserID, CreateSimClause()) == false){
            message = poTeleApp.getMessage(); //get error message from request
        }
    }
    private String CreateSimClause(){
        //INITIALIZE SIM CARD NAMES
        if (loSubscriber.InitSim() == false){
            message = loSubscriber.getMessage();
            return null;
        }

        //GET SIM CARD NAMES EACH SLOT
        String sim1 = loSubscriber.getSimSlot1();
        String sim2 = loSubscriber.getSimSlot2();

        //CREATE SQL CONDITION OF 'cSubscrbr' COLUMN FOR FILTERING OF CLIENTS TO BE IMPORTED
        String simCondition = null;
        if(sim1 != null && sim2 != null){
            simCondition= "(cSubscrbr IN ("+loConstants.getSimSubscriber(sim1)+","+loConstants.getSimSubscriber(sim2)+"))";
        }else {
            if (sim1 != null){
                simCondition=  "(cSubscrbr = "+loConstants.getSimSubscriber(sim1)+")";
            } else if (sim2 != null) {
                simCondition=  "(cSubscrbr = "+loConstants.getSimSubscriber(sim2)+")";
            }
        }
        return simCondition;
    }
    public LiveData<List<ELeadCalls>> GetLeads(String sLeadsrc){
        return poDaoLeadCalls.GetLiveLeadCalls(poSession.getUserID(), CreateSimClause(), loConstants.getLeadsrc(sLeadsrc));
    }
    public LiveData<List<EClient2Call>> GetClientsInfo(String sClientID){
        return poDaoClient2Call.GetLiveClientInfo(sClientID);
    }
    public LiveData<List<EMCInquiry>> GetMCInquiry(String sReferNox){
        return poDaomcInquiry.GetLiveMCInquiry(sReferNox);
    }
}
