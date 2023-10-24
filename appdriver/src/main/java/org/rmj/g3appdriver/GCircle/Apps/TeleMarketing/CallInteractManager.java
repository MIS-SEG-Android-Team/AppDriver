package org.rmj.g3appdriver.GCircle.Apps.TeleMarketing;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LiveData;

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
    private DAOClient2Call poDaoClient2Call;
    private DAOMCInquiry poDaomcInquiry;
    private GTeleConstants loConstants;
    private GSimSubscriber loSubscriber;
    private String message;
    public CallInteractManager(Application instance) {
        this.context = instance.getApplicationContext();

        this.poTeleApp = new GTeleApp(instance);
        this.poSession = EmployeeSession.getInstance(instance);

        this.poDaoLeadCalls = GGC_GCircleDB.getInstance(instance).teleLeadsDao();
        this.poDaoClient2Call = GGC_GCircleDB.getInstance(instance).teleCallClientsDao();
        this.poDaomcInquiry = GGC_GCircleDB.getInstance(instance).teleMCInquiryDao();

        this.loSubscriber = new GSimSubscriber(instance.getApplicationContext());
        this.loConstants = new GTeleConstants();
    }
    //1. GET LEADS FIRST (CLIENT_ID -> CLIENT INFO, STRANSNOX -> PRODUCT INQUIRY)
    public LiveData<DAOLeadCalls.LeadInformation> GetLeads(){
        String sUserID = poSession.getUserID();
        String sSubscrFilter = CreateSimClause();
        String dTransact= new
                SimpleDateFormat("yyyy:MM:dd", Locale.getDefault()).format(Calendar.getInstance().getTime());

        /*LEADS ARE BASED ON DEFAULT PRIORITIES, REFERRED FROM VB.NET TELEMARKETING DESKTOP CODE*/
        if (poDaoLeadCalls.CountLeads(sUserID,sSubscrFilter,
                loConstants.GetLeadConstant("GANADO")) > 0){ //GANADO SOURCE
            return poDaoLeadCalls.GetLiveLeadCall(sUserID, sSubscrFilter, loConstants.GetLeadConstant("GANADO"), dTransact);
        }else if (poDaoLeadCalls.CountLeads(sUserID,sSubscrFilter,
                loConstants.GetLeadConstant("MC CREDIT APPLICATION")) > 0){ //MC CREDIT APP SOURCE
            return poDaoLeadCalls.GetLiveLeadCall(sUserID, sSubscrFilter, loConstants.GetLeadConstant("MC CREDIT APPLICATION"), dTransact);
        }else if (poDaoLeadCalls.CountLeads(sUserID,sSubscrFilter,
                loConstants.GetLeadConstant("MC INQUIRY")) > 0){ //INQUIRY SOURCE
            return poDaoLeadCalls.GetLiveLeadCall(sUserID, sSubscrFilter, loConstants.GetLeadConstant("MC INQUIRY"), dTransact);
        }else if (poDaoLeadCalls.CountLeads(sUserID,sSubscrFilter,
                loConstants.GetLeadConstant("RANDOM CALL")) > 0){ //RANDOM CALL SOURCE
            return poDaoLeadCalls.GetLiveLeadCall(sUserID, sSubscrFilter, loConstants.GetLeadConstant("RANDOM CALL"), dTransact);
        }else if (poDaoLeadCalls.CountLeads(sUserID,sSubscrFilter,
                loConstants.GetLeadConstant("REFERRAL")) > 0){ //REFERRAL SOURCE
            return poDaoLeadCalls.GetLiveLeadCall(sUserID, sSubscrFilter, loConstants.GetLeadConstant("REFERRAL"), dTransact);
        }else if (poDaoLeadCalls.CountLeads(sUserID,sSubscrFilter,
                loConstants.GetLeadConstant("OTHERS")) > 0){ //OTHERS SOURCE
            return poDaoLeadCalls.GetLiveLeadCall(sUserID, sSubscrFilter, loConstants.GetLeadConstant("OTHERS"), dTransact);
        }else if (poDaoLeadCalls.CountLeads(sUserID,sSubscrFilter,
                loConstants.GetLeadConstant("BIYAHENG FIESTA")) > 0){ //BYAHENG SOURCE
            return poDaoLeadCalls.GetLiveLeadCall(sUserID, sSubscrFilter, loConstants.GetLeadConstant("BIYAHENG FIESTA"), dTransact);
        }else if (poDaoLeadCalls.CountLeads(sUserID,sSubscrFilter,
                loConstants.GetLeadConstant("FREE SERVICE")) > 0){ //FREE SERVICE SOURCE
            return poDaoLeadCalls.GetLiveLeadCall(sUserID, sSubscrFilter, loConstants.GetLeadConstant("FREE SERVICE"), dTransact);
        }else if (poDaoLeadCalls.CountLeads(sUserID,sSubscrFilter,
                loConstants.GetLeadConstant("DISPLAY CARAVAN")) > 0){ //DISPLAY CARAVAN SOURCE
            return poDaoLeadCalls.GetLiveLeadCall(sUserID, sSubscrFilter, loConstants.GetLeadConstant("DISPLAY CARAVAN"), dTransact);
        }else if (poDaoLeadCalls.CountLeads(sUserID,sSubscrFilter,
                loConstants.GetLeadConstant("INCOMING CALL")) > 0){ //INCOMING SOURCE
            return poDaoLeadCalls.GetLiveLeadCall(sUserID, sSubscrFilter, loConstants.GetLeadConstant("INCOMING CALL"), dTransact);
        }
        return null;
    }
    public String getMessage(){
        return message;
    }
    public void ImportCalls(){
        //IMPORT LEADS WITH CLIENT INFO
        if (poTeleApp.ImportLeads(poSession.getUserID(), CreateSimClause()) == false){
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
            simCondition= "(cSubscrbr IN ("+loConstants.GetSimSubscriber(sim1)+","+loConstants.GetSimSubscriber(sim2)+"))";
        }else {
            if (sim1 != null){
                simCondition=  "(cSubscrbr = "+loConstants.GetSimSubscriber(sim1)+")";
            } else if (sim2 != null) {
                simCondition=  "(cSubscrbr = "+loConstants.GetSimSubscriber(sim2)+")";
            }
        }
        return simCondition;
    }
}
