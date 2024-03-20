package org.rmj.g3appdriver.GCircle.Apps.TeleMarketing;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Account.EmployeeSession;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Telemarketing.classobj.GSimSubscriber;
import org.rmj.g3appdriver.lib.Telemarketing.classobj.GTeleApp;
import org.rmj.g3appdriver.lib.Telemarketing.constants.GTeleConstants;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOClientMobile;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOHoutlineOutgoing;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOLeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOMCInquiry;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOPriorities;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClientMobile;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EHotline_Outgoing;
import org.rmj.g3appdriver.lib.Telemarketing.entities.ELeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EMCInquiry;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EPriorities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CallInteractManager {
    private static final String TAG = "CallInteractManager";
    private Context context;
    private GTeleApp poTeleApp;
    private EmployeeSession poSession;
    private DAOLeadCalls poDaoLeadCalls;
    private DAOClient2Call poDaoClient;
    private DAOClientMobile poDaoClientMobile;
    private DAOMCInquiry poDaoMcInq;
    private DAOHoutlineOutgoing poDaoHOutgoing;
    private DAOPriorities poDaoPriorities;
    private GTeleConstants loConstants;
    private GSimSubscriber loSubscriber;
    private String sTransNox;
    private String sReferNox;
    private String sLeadSrc;
    private String sClientID;
    private String sMobileNo;
    private String cTranStat;
    public String sim1;
    public String sim2;
    private String cSubscr;
    public String simCondition;
    private String dToday;
    private String message;
    private String sCallStrt;
    private String sCallEnd;
    private long lHourDuration;
    private long lMinDuration;
    private long lSecDuration;
    public CallInteractManager(Application instance) {
        this.context = instance.getApplicationContext();

        this.poTeleApp = new GTeleApp(instance);
        this.poSession = EmployeeSession.getInstance(instance);

        this.poDaoLeadCalls = GGC_GCircleDB.getInstance(instance).teleLeadsDao();
        this.poDaoClient = GGC_GCircleDB.getInstance(instance).teleCallClientsDao();
        this.poDaoClientMobile = GGC_GCircleDB.getInstance(instance).teleClientMobDao();
        this.poDaoMcInq = GGC_GCircleDB.getInstance(instance).teleMCInquiryDao();
        this.poDaoHOutgoing = GGC_GCircleDB.getInstance(instance).teleHOutgoingDao();
        this.poDaoPriorities = GGC_GCircleDB.getInstance(instance).telePriorities();

        this.loSubscriber = new GSimSubscriber(instance.getApplicationContext());
        this.loConstants = new GTeleConstants();
    }
    public String getMessage(){
        return message;
    }
    public Boolean GetSimCards(){ //INITIALIZE SIM CARD NAMES
        if (loSubscriber.InitSim() == false){
            message = loSubscriber.getMessage();
            return false;
        }

        //GET SIM CARD NAMES EACH SLOT
        sim1 = loSubscriber.getSimSlot1();
        sim2 = loSubscriber.getSimSlot2();

        if (sim1.isEmpty() && sim2.isEmpty()){
            message = "No simcards detected by the app";
            return false;
        }

        //INITIALIZE FINAL VALUE FOR VARIABLES
        if (!sim1.isEmpty()){
            sim1 = String.valueOf(loConstants.GetSimSubscriber(sim1));
            simCondition=  "(cSubscrbr = '"+sim1+"')";
        } else if (sim2.isEmpty()) {
            sim2 = String.valueOf(loConstants.GetSimSubscriber(sim2));
            simCondition=  "(cSubscrbr = '"+sim2+"')";
        }

        if(sim1 != null && sim2 != null){
            simCondition= "(cSubscrbr IN ("+sim1+","+sim2+"))";
        }
        return true;
    }
    public Boolean ImportCalls(){ //IMPORT LEADS WITH CLIENT INFO, PRODUCT INQUIRY
        try {
            if (simCondition.isEmpty()){
                message = "Failed to import contacts. Please check your simcards";
                return false;
            }

            JSONArray loLeads = poTeleApp.GetLeads(poSession.getUserID(), simCondition);
            if (loLeads == null){
                message = poTeleApp.getMessage();
                return false;
            }

            for (int i = 0; i < loLeads.length(); i++){
                JSONObject loLeadInfo = loLeads.getJSONObject(i).getJSONObject("leads");

                String sTransNox = loLeadInfo.get("sTransNox").toString();

                //INITIALIZE ENTITY COLUMNS
                ELeadCalls eLeadCalls = new ELeadCalls();
                eLeadCalls.setsTransNox(sTransNox);
                eLeadCalls.setsAgentIDx(loLeadInfo.get("sAgentIDx").toString());
                eLeadCalls.setdTransact(loLeadInfo.get("dTransact").toString());
                eLeadCalls.setsClientID(loLeadInfo.get("sClientID").toString());
                eLeadCalls.setsMobileNo(loLeadInfo.get("sMobileNo").toString());
                eLeadCalls.setsRemarksx(loLeadInfo.get("sRemarksx").toString());
                eLeadCalls.setsReferNox(loLeadInfo.get("sReferNox").toString());
                eLeadCalls.setsSourceCD(loLeadInfo.get("sSourceCD").toString());
                eLeadCalls.setsApprovCd(loLeadInfo.get("sApprovCd").toString());
                eLeadCalls.setcTranStat(loLeadInfo.get("cTranStat").toString());
                eLeadCalls.setdCallStrt(loLeadInfo.get("dCallStrt").toString());
                eLeadCalls.setdCallEndx(loLeadInfo.get("dCallEndx").toString());
                eLeadCalls.setnNoRetryx(Integer.valueOf(loLeadInfo.get("nNoRetryx").toString()));
                eLeadCalls.setcSubscrbr(loLeadInfo.get("cSubscrbr").toString());
                eLeadCalls.setcCallStat(loLeadInfo.get("cCallStat").toString());
                eLeadCalls.setcTLMStatx(loLeadInfo.get("cTLMStatx").toString());
                eLeadCalls.setcSMSStatx(loLeadInfo.get("cSMSStatx").toString());
                eLeadCalls.setnSMSSentx(Integer.valueOf(loLeadInfo.get("nSMSSentx").toString()));
                eLeadCalls.setsModified(loLeadInfo.get("sModified").toString());
                eLeadCalls.setdModified(loLeadInfo.get("dModified").toString());

                //GET EXISTING RECORD ON LOCAL DB, IF 0 'SAVE' ELSE 'UPDATE'
                if (poDaoLeadCalls.GetLeadTrans(sTransNox) == null){
                    poDaoLeadCalls.SaveLeads(eLeadCalls);
                }else {
                    poDaoLeadCalls.UpdateLeads(eLeadCalls);
                }
            }
            message= "Lead Calls imported successfully to device";
            return true;
        }catch(Exception e){
            message= e.getMessage();
            return false;
        }
    }
    public Boolean ImportPriorities(){
        try {
            JSONArray loPriorities = poTeleApp.GetPriorities();
            if (loPriorities == null){
                message = poTeleApp.getMessage();
                return false;
            }

            for (int i = 0; i < loPriorities.length(); i++){
                JSONObject loInfo = loPriorities.getJSONObject(i).getJSONObject("priorities");

                int index = (int) loInfo.get("index");
                String sSourceCD = loInfo.get("sSourceCD").toString();

                //INITIALIZE ENTITY COLUMNS
                EPriorities ePriorities = new EPriorities();
                ePriorities.setIndex(index);
                ePriorities.setsSourceCD(sSourceCD);

                //GET EXISTING RECORD ON LOCAL DB, IF 0 'SAVE' ELSE 'UPDATE'
                if (poDaoPriorities.GetByIndex(index) == null){
                    poDaoPriorities.SavePriorities(ePriorities);
                }else {
                    poDaoPriorities.UpdatePriorities(ePriorities);
                }
            }
            message= "Priorities imported successfully to device";
            return true;
        }catch(Exception e){
            message= e.getMessage();
            return false;
        }
    }

    /** REQUIRED: NEED TO INITIALIZE FIRST TRANSACTION NO, BEFORE SAVING/UPDATING TRANSACTIONS*/
    public void InitTransaction(LeadsInformation loLeads){
        Date dcurrDt = Calendar.getInstance().getTime();
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String frmDt = sdFormat.format(dcurrDt);

        this.sTransNox = loLeads.getsTransNox();
        this.sReferNox = loLeads.getsReferNox();
        this.sLeadSrc = loLeads.getsSourceCD();
        this.sClientID = loLeads.getsClientID();
        this.sMobileNo = loLeads.getsMobileNo();
        this.cTranStat = loLeads.getcTranStat();
        this.cSubscr = loLeads.getsSubscr();
        this.dToday = frmDt;
    }
    public Boolean ChangeStatus(String status){
        //validate first if transaction no is applied
        if (sTransNox.isEmpty()){
            message = "No applied transaction number";
            return false;
        }

        if (!poTeleApp.UpdateStatus(sTransNox, poSession.getUserID(), status)){
            message = poTeleApp.getMessage();
            return false;
        }

        poDaoLeadCalls.UpdateStatus(sTransNox, poSession.getUserID(), status, dToday);

        return true;
    }
    public Boolean SaveClient2Call() {
        try {
            JSONObject loParam = new JSONObject();
            loParam.put("sClientID", sClientID);

            if (loParam == null){
                message = "No client informations to import";
                return false;
            }

            //GET RESULTS FROM SERVER
            JSONObject loClients = poTeleApp.GetClients(loParam);
            if (loClients == null){
                message = poTeleApp.getMessage();
                return false;
            }

            String sClientid = loClients.get("sClientID").toString();

            //initialize entity column values
            EClient2Call eClient2Call = new EClient2Call();
            eClient2Call.setsClientID(sClientid);
            eClient2Call.setsClientNM(loClients.get("sCompnyNm").toString());
            eClient2Call.setxAddressx(loClients.get("xAddressx").toString());
            eClient2Call.setsMobileNox(loClients.get("sMobileNo").toString());
            eClient2Call.setsPhoneNox(loClients.get("sPhoneNox").toString());

            //get exisitng record on local database, if 0 then insert else update
            if (poDaoClient.GetClient2Call(sClientid) == null){
                poDaoClient.SaveClients(eClient2Call);
            }else {
                poDaoClient.UpdateClients(eClient2Call);
            }

            message = "Client's Info saved to device";
            return true;
        }catch (Exception e){
            message = e.getMessage();
            return false;
        }
    }
    public Boolean SaveClientMobile(){
        try {
            JSONObject loParams = new JSONObject();
            loParams.put("sClientID",sClientID);
            loParams.put("sMobileNo",sMobileNo);

            if (loParams == null){
                message = "No client's mobile info to import";
                return false;
            }

            JSONObject loMobiles = poTeleApp.GetCLientMobile(loParams);
            if (loMobiles == null){
                message = poTeleApp.getMessage();
                return false;
            }

            String sClientId = loMobiles.get("sClientID").toString();
            String sMobile = loMobiles.get("sMobileNo").toString();

            EClientMobile eClientMobile = new EClientMobile();

            eClientMobile.setsClientID(sClientId);
            eClientMobile.setnEntryNox(Integer.valueOf(loMobiles.get("nEntryNox").toString()));
            eClientMobile.setsMobileNo(sMobile);
            eClientMobile.setnPriority(Integer.valueOf(loMobiles.get("nPriority").toString()));
            eClientMobile.setcIncdMktg(loMobiles.get("cIncdMktg").toString());
            eClientMobile.setnUnreachx(Integer.valueOf(loMobiles.get("nUnreachx").toString()));
            eClientMobile.setdLastVeri(loMobiles.get("dLastVeri").toString());
            eClientMobile.setdInactive(loMobiles.get("dInactive").toString());
            eClientMobile.setnNoRetryx(Integer.valueOf(loMobiles.get("nNoRetryx").toString()));
            eClientMobile.setcInvalidx(loMobiles.get("cInvalidx").toString());
            eClientMobile.setsIdleTime(loMobiles.get("sIdleTime").toString());
            eClientMobile.setcConfirmd(loMobiles.get("cConfirmd").toString());
            eClientMobile.setdConfirmd(loMobiles.get("dConfirmd").toString());
            eClientMobile.setcSubscr(loMobiles.get("cSubscrbr").toString());
            eClientMobile.setdHoldMktg(loMobiles.get("dHoldMktg").toString());
            eClientMobile.setdLastCall(loMobiles.get("dLastCall").toString());
            eClientMobile.setcRecdStat(loMobiles.get("cRecdStat").toString());

            if (poDaoClientMobile.GetClientMobile(sClientId, sMobile) == null){
                poDaoClientMobile.SaveClientMobile(eClientMobile);
            }else {
                poDaoClientMobile.UpdateClientMobile(eClientMobile);
            }

            message = "Client's Mobile saved to device";
            return true;
        }catch (Exception e){
            message = e.getMessage();
            return false;
        }
    }
    public Boolean SaveInquiries(){
        try {
            JSONObject loParams = new JSONObject();
            loParams.put("sReferNox",sReferNox);
            loParams.put("sSourceCd",sLeadSrc);

            if (loParams == null){
                message = "No inquiries to import";
                return false;
            }

            JSONObject loInq = poTeleApp.GetMCInquiries(loParams);
            if (loInq == null){
                message = poTeleApp.getMessage();
                return false;
            }

            String sTransNox = loInq.get("sTransnox").toString();

            //initialize entity column values
            EMCInquiry emcInquiry = new EMCInquiry();

            emcInquiry.setsTransNox(sTransNox);
            emcInquiry.setdFollowUp(loInq.get("dFollowUp").toString());
            emcInquiry.setsClientID(loInq.get("sClientID").toString());
            emcInquiry.setsBrandIDx(loInq.get("sBrandIDx").toString());
            emcInquiry.setsModelIDx(loInq.get("sModelIDx").toString());
            emcInquiry.setsColorIDx(loInq.get("sColorIDx").toString());
            emcInquiry.setcApplType(loInq.get("cApplType").toString());
            emcInquiry.setnTerms(Integer.valueOf(loInq.get("nTerms").toString()));
            emcInquiry.setdTargetxx(loInq.get("dTargetxx").toString());
            emcInquiry.setnDownPaym(Double.valueOf(loInq.get("nDownPaym").toString()));
            emcInquiry.setnMonAmort(Double.valueOf(loInq.get("nMonAmort").toString()));
            emcInquiry.setnCashPrc(Double.valueOf(loInq.get("nCashPrc").toString()));
            emcInquiry.setsRelatnID(loInq.get("sRelatnID").toString());
            emcInquiry.setsTableNM(loInq.get("sTableNM").toString());

            if (poDaoMcInq.GetMCInquiry(sTransNox) == null){
                poDaoMcInq.SaveMCInq(emcInquiry);
            }else {
                poDaoMcInq.UpdateMCInq(emcInquiry);
            }

            message = "Inquiries saved to device";
            return true;
        }catch (Exception e){
            message = e.getMessage();
            return false;
        }
    }
    public void InitQueue(String sCallStrt, String sCallEnd) throws ParseException {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date dCallStrt = timeFormat.parse(sCallStrt);
        Date dCallEnd = timeFormat.parse(sCallEnd);

        long lDuration = dCallEnd.getTime() - dCallStrt.getTime();

        this.sCallStrt = sCallStrt;
        this.sCallEnd = sCallEnd;

        this.lHourDuration = TimeUnit.HOURS.convert(lDuration, TimeUnit.MILLISECONDS);
        this.lMinDuration = TimeUnit.MINUTES.convert(lDuration, TimeUnit.MILLISECONDS);
        this.lSecDuration = TimeUnit.SECONDS.convert(lDuration, TimeUnit.MILLISECONDS);
    }
    public Boolean SaveCallStatus(String sCallStat, String callAction, String sApprvCD, String sRemarks){
        try {
            //validate first if transaction no is applied
            if (sTransNox == null){
                message = "No applied transaction number";
                return false;
            }

            //send json params for web request and get response
            JSONObject loTransParams = poTeleApp.SendCallStatus(sCallStat, callAction, sTransNox, cSubscr,
                    sApprvCD, poSession.getUserID(), sClientID, sMobileNo, sCallStrt, sCallEnd, sRemarks);

            if (loTransParams == null){
                message = poTeleApp.getMessage();
                return false;
            }

            String dTransact = loTransParams.get("dTransact").toString();

            //'POSSIBLE SALES' AND 'NOT NOW' STATUS, INSERT TO HOTLINE_OUTGOING
            if (sCallStat == "PS" || sCallStat == "NN" || sCallStat == "CB"){
                String sHOutgoingNox = loTransParams.get("sTransNox").toString();

                if (sHOutgoingNox != null && !sHOutgoingNox.isEmpty()){
                    InsertHotlineOutgoing(sHOutgoingNox,
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
                            poSession.getUserID());

                    message = poTeleApp.getMessage();
                }else {
                    message = "No generated transaction no for Hotline_Outgoing";
                    return false;
                }
            }

            //UPDATE NUNREACHX, IF STATUS 'CANNOT BE REACHED', ELSE 0
            int nUnreachx = 0;
            if (sCallStat == "UR"){
                nUnreachx = 1;
                message= getMessage() + " Status: Unreachable";
            }
            //UPDATE CLIENT MOBILE, IMPORT DATA AS NEW ROW IF NOT FOUND ON LOCAL
            poDaoClientMobile.UpdateCallTrans(sClientID, sMobileNo, dTransact, nUnreachx);
            poDaoLeadCalls.UpdateLeadCall(sTransNox, sCallStat, callAction, sApprvCD, sCallStrt,
                    sCallEnd, poSession.getUserID(), dToday);

            message = "Call transaction has been saved to device";
            return true;
        } catch (Exception e) {
            message = e.getMessage();
            return false;
        }
    }
    public Boolean SaveSchedule(String dFollowUp, String cTranstat, String sRemarks){
        try {
            if (sReferNox == null){
                message = "No applied transaction no";
                return false;
            }

            if (dFollowUp == null){
                message = "No schedule date applied";
                return false;
            }

            JSONObject loSchedule =  poTeleApp.UploadSchedule(sReferNox, sLeadSrc, dFollowUp, cTranstat, loConstants.GetRemarks(sRemarks), poSession.getUserID());
            if (loSchedule == null){
                message = poTeleApp.getMessage();
                return false;
            }

            String loTransNox = loSchedule.get("sTransNox").toString();
            String loFollowUp = loSchedule.get("dFollowUp").toString();

            poDaoMcInq.UpdateFollowUp(loFollowUp, loTransNox);

            message = "Schedule has been saved on device";
            return true;
        }catch (Exception e){
            message = e.getMessage();
            return false;
        }
    }
    public Boolean InsertHotlineOutgoing(String sTransNox, String dTransact,String sDivision, String sMobileNo,
                                         String sMessagex, String cSubscr, String dDueDate, String cSendStat,
                                         int nNoRetryx, String sUDHeader, String sReferNox, String sSourceCd,
                                         String cTranStat, int nPriority, String sUserID){

        EHotline_Outgoing eHotlineOutgoing = new EHotline_Outgoing();
        eHotlineOutgoing.setsTransNox(sTransNox);
        eHotlineOutgoing.setdTransact(dTransact);
        eHotlineOutgoing.setsDivision(sDivision);
        eHotlineOutgoing.setsMobileNo(sMobileNo);
        eHotlineOutgoing.setsMessagex(sMessagex);
        eHotlineOutgoing.setcSubscrbr(cSubscr);
        eHotlineOutgoing.setdDueUntil(dDueDate);
        eHotlineOutgoing.setcSendStat(cSendStat);
        eHotlineOutgoing.setnNoRetryx(nNoRetryx);
        eHotlineOutgoing.setsUDHeader(sUDHeader);
        eHotlineOutgoing.setsReferNox(sReferNox);
        eHotlineOutgoing.setsSourceCd(sSourceCd);
        eHotlineOutgoing.setcTranStat(cTranStat);
        eHotlineOutgoing.setnPriority(nPriority);
        eHotlineOutgoing.setsModified(sUserID);
        eHotlineOutgoing.setdModified(dTransact);

        //GET EXISTING RECORD ON LOCAL DB, IF 0 'SAVE' ELSE 'UPDATE'
        if (poDaoHOutgoing.GetHotlineOutgoing(sTransNox) == null){
            poDaoHOutgoing.SaveHotlineOutgoing(eHotlineOutgoing);
        }else {
            poDaoHOutgoing.UpdateHotlineOutgoing(eHotlineOutgoing);
        }

        message = "Transaction has been saved to device";
        Log.d(TAG, "Table: Hotline_Outgoing Transaction No: " + sTransNox);
        return true;
    }
    public void RemoveCallSession(){
        poDaoLeadCalls.RemoveLeads();
        poDaoPriorities.RemovePriorities();
        poDaoClient.RemoveClient2Call();
        poDaoClientMobile.RemoveClientMobile();
        poDaoMcInq.RemoveInquiries();
        poDaoHOutgoing.RemoveHOutgoing();
    }
    public LiveData<DAOLeadCalls.LeadInformation> GetLeadQueues(){
        return poDaoLeadCalls.GetInitLead(poSession.getUserID(), sim1, sim2);
    }
    public LiveData<DAOLeadCalls.LeadDetails> GetLeadDetails(){ //ON QUEUES, PRIORITY CALLS
        return poDaoLeadCalls.GetLeadDetails(sReferNox);
    }
    public LiveData<List<DAOLeadCalls.LeadHistory>> GetHistory(){
        return poDaoLeadCalls.GetCallHistory(poSession.getUserID());
    }
    public LiveData<DAOMCInquiry.ProductInfo> GetProductDetails(){
        return poDaoMcInq.GetMCInquiryDetails(sReferNox);
    }
}
