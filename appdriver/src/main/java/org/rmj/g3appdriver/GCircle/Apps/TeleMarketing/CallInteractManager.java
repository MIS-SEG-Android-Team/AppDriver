package org.rmj.g3appdriver.GCircle.Apps.TeleMarketing;

import static android.content.Context.TELEPHONY_SERVICE;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.Account.EmployeeSession;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
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
    private GTeleApp gTeleApp;
    private EmployeeSession poSession;
    private DAOLeadCalls poDaoLeadCalls;
    private DAOClient2Call poDaoClient2Call;
    private DAOMCInquiry poDaomcInquiry;
    private GTeleConstants teleConstants;
    private String message;
    public CallInteractManager(Application instance) {
        this.context = instance.getApplicationContext();

        this.gTeleApp = new GTeleApp(instance);
        this.poSession = EmployeeSession.getInstance(instance);
        this.poDaoLeadCalls = GGC_GCircleDB.getInstance(instance.getApplicationContext()).teleLeadsDao();
        this.poDaoClient2Call = GGC_GCircleDB.getInstance(instance.getApplicationContext()).teleCallClientsDao();
        this.poDaomcInquiry = GGC_GCircleDB.getInstance(instance.getApplicationContext()).teleMCInquiry();
        this.teleConstants = new GTeleConstants();
    }
    public String getMessage(){
        return message;
    }
    //REQUIRES DUAL SIM CARD SLOTS
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getSimSubscr(int simIndex){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);

        //higher sdk version, dual sim slot
        int simState = telephonyManager.getSimState(simIndex);

        switch (simState){
            case TelephonyManager.SIM_STATE_ABSENT:
                message= "NO SIM CARD DETECTED FOR SLOT "+ simIndex;
                return null;
            case TelephonyManager.SIM_STATE_NOT_READY:
                message= "SIM CARD NOT READY "+ simIndex;
                return null;
            case TelephonyManager.SIM_STATE_READY:
                return telephonyManager.getSimOperatorName();
        }
        return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String CreateSimClause(){
        //get simcard carrier names on each slot
        String sim1 = getSimSubscr(1);
        String sim2 = getSimSubscr(2);

        //initiate condition of 'cSubscrbr' column for filtering data for clients to call
        String simCondition = null;
        if(sim1 != null && sim2 != null){
            //sql condition if both slot have simcards
            simCondition= "(cSubscrbr IN ("+teleConstants.getSimSubscriber(sim1)+","+teleConstants.getSimSubscriber(sim2)+"))";
        }else {
            //sql condition if one slot have simcard
            if (sim1 != null){
                simCondition=  "(cSubscrbr = "+teleConstants.getSimSubscriber(sim1)+")";
            } else if (sim2 != null) {
                simCondition=  "(cSubscrbr = "+teleConstants.getSimSubscriber(sim2)+")";
            }
        }
        return simCondition;
    }

    //REQUIRES DUAL SIM CARD SLOTS
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Init(){
        String sUserID = poSession.getUserID();
        //import leads with client info from (server tablename: Call_Outgoing)
        if (gTeleApp.ImportLeads(sUserID, CreateSimClause()) == false){
            message = gTeleApp.getMessage(); //get error message from request
        }
        //import mc inquiries from (server tablename: MC_Product_Inquiry)
        if (gTeleApp.ImportMCInquiries() == false){
            message = gTeleApp.getMessage(); //get error message from request
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LiveData<List<ELeadCalls>> GetLeads(String sLeadsrc){
        return poDaoLeadCalls.GetLeadCalls(poSession.getUserID(), CreateSimClause(), teleConstants.getLeadsrc(sLeadsrc));
    }
    public LiveData<List<EClient2Call>> GetClientsInfo(String sClientID){
        return poDaoClient2Call.GetClientInfo(sClientID);
    }
    public LiveData<List<EMCInquiry>> GetMCInquiry(String sReferNox){
        return poDaomcInquiry.GetLiveMCInquiry(sReferNox);
    }
}
