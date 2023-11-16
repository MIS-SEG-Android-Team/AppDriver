package org.rmj.g3appdriver.GCircle.Apps.TeleMarketing;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CallInteractManager {
    private static final String TAG = "CallInteractManager";
    private Context context;
    private GTeleApp poTeleApp;
    private EmployeeSession poSession;
    private DAOLeadCalls poDaoLeadCalls;
    private GTeleConstants loConstants;
    private GSimSubscriber loSubscriber;
    private String sTransNox;
    private String sLeadSrc;
    private String sUserIDx;
    private String sClientID;
    private String sMobileNo;
    private String cSubscr;
    private String message;
    public CallInteractManager(Application instance) {
        this.context = instance.getApplicationContext();

        this.poTeleApp = new GTeleApp(instance);
        this.poSession = EmployeeSession.getInstance(instance);
        this.poDaoLeadCalls = GGC_GCircleDB.getInstance(instance).teleLeadsDao();
        this.loSubscriber = new GSimSubscriber(instance.getApplicationContext());
        this.loConstants = new GTeleConstants();
    }
    public String getMessage(){
        return message;
    }
    private String CreateSubscrCondition(){ //INITIALIZE SIM CARD NAMES
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
            simCondition= "(cSubscrbr IN ("+loConstants.GetSimSubscriber(sim1)+","+loConstants.GetSimSubscriber(sim2)+"))";
        }else {
            if (sim1 != null){
                simCondition=  "(cSubscrbr = '"+loConstants.GetSimSubscriber(sim1)+"')";
            } else if (sim2 != null) {
                simCondition=  "(cSubscrbr = '"+loConstants.GetSimSubscriber(sim2)+"')";
            }
        }
        return simCondition;
    }
    public void ImportCalls(){ //IMPORT LEADS WITH CLIENT INFO, PRODUCT INQUIRY
        if (poTeleApp.ImportLeads(poSession.getUserID(), CreateSubscrCondition()) == false){
            message = poTeleApp.getMessage(); //get error message from request
        }
    }
    /* REQUIRED: NEED TO INITIALIZE FIRST TRANSACTION NO, BEFORE SAVING TRANSACTIONS*/
    public void InitTransaction(String sTransNox){
        ELeadCalls leadCalls = poTeleApp.GetLeadTrans(sTransNox);
        this.sTransNox = sTransNox;
        this.sLeadSrc = leadCalls.getsSourceCD();
        this.sClientID = leadCalls.getsClientID();
        this.sMobileNo = leadCalls.getsMobileNo();
        this.cSubscr = leadCalls.getcSubscrbr();
        this.sUserIDx = poSession.getUserID();
    }
    public boolean SaveCallStatus(String sCallStat, String sApprvCD){
        try {
            //validate first if transaction no is applied
            if (sTransNox == null){
                message = "No applied transaction number";
                return false;
            }

            //send json params for web request and get response
            JSONObject jsonResponse = poTeleApp.SendCallStatus(sCallStat, sTransNox, cSubscr,
                    sApprvCD, sUserIDx, sClientID, sMobileNo);

            if (jsonResponse == null){
                message = poTeleApp.getMessage();
                return false;
            }

            JSONObject loTransParams = jsonResponse.getJSONObject("transparams");
            String dTransact = loTransParams.get("dTransact").toString();

            //'POSSIBLE SALES' AND 'NOT NOW' STATUS, INSERT TO HOTLINE_OUTGOING
            if (sCallStat == "POSSIBLE SALES" || sCallStat == "NOT NOW"){
                String sHOutgoingNox = loTransParams.get("sTransNox").toString();

                if (sHOutgoingNox != null || sHOutgoingNox.isEmpty()){
                    poTeleApp.InsertHotlineOutgoing(sHOutgoingNox,
                            dTransact,
                            loTransParams.get("sDivision").toString(),
                            sMobileNo,
                            loTransParams.get("sMessagex").toString(),
                            cSubscr,
                            loTransParams.get("dDueDate").toString(),
                            loTransParams.get("cSendStat").toString(),
                            Integer.valueOf(loTransParams.get("nNoRetryx").toString()),
                            loTransParams.get("sUDHeader").toString(),
                            sTransNox,
                            loTransParams.get("sSourceCd").toString(),
                            loTransParams.get("cTranStat").toString(),
                            Integer.valueOf(loTransParams.get("nPriority").toString()),
                            sUserIDx);

                    message = poTeleApp.getMessage();
                }else {
                    message = "No generated transaction no for Hotline_Outgoing";
                    return false;
                }
            }

            //UPDATE NUNREACHX, IF STATUS 'CANNOT BE REACHED', ELSE 0
            int nUnreachx = 0;
            if (sCallStat == "CANNOT BE REACHED"){
                nUnreachx = 1;
                message= poTeleApp.getMessage() + " Status: Unreachable";
            }
            //UPDATE CLIENT MOBILE, IMPORT DATA AS NEW ROW IF NOT FOUND ON LOCAL
            if (poTeleApp.UpdateCMobile(sClientID, sMobileNo, dTransact, nUnreachx) == false){
                message = poTeleApp.getMessage();
                return false;
            }

            //UPDATE LEAD'S CTLMSTATX TO SELECTED STATUS
            if (poTeleApp.UpdateLeadCallStat(sTransNox, sCallStat) == false){
                message = poTeleApp.getMessage();
                return false;
            }

            message = "Call transaction has been saved";
            return true;
        } catch (Exception e) {
            message = e.getMessage();
            return false;
        }
    }
    public boolean SaveSchedule(String dFollowUp, String cTranstat, String sRemarks){
        if (sTransNox == null){
            message = "No applied transaction no";
            return false;
        }

        if (dFollowUp == null){
            message = "No schedule date applied";
            return false;
        }

        return poTeleApp.UploadSchedule(sTransNox, sLeadSrc, dFollowUp, cTranstat, sRemarks, sUserIDx);
    }
    public LiveData<DAOLeadCalls.LeadInformation> GetLeads(String sLeadSrc){ //ON QUEUES, PRIORITY CALLS
        return poDaoLeadCalls.GetLiveLeadCall(poSession.getUserID(), CreateSubscrCondition(), loConstants.GetLeadConstant(sLeadSrc));
    }
    public LiveData<DAOLeadCalls.LeadInformation> GetLeadsOnSched() { //ON SCHED, LEAST PRIORITY AFTER QUEUES
        String dTransact = new
                SimpleDateFormat("yyyy:MM:dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        return poDaoLeadCalls.GetLiveSchedCall(poSession.getUserID(), CreateSubscrCondition(), dTransact);
    }
    public LiveData<List<DAOLeadCalls.LeadHistory>> GetHistory(){
        return poDaoLeadCalls.GetLeadHistory();
    }
}
