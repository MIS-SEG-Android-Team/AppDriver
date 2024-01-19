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
    private String sUserIDx;
    private String sClientID;
    private String sMobileNo;
    private String sim1;
    private String sim2;
    private String cSubscr;
    private String simCondition;
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

        //CREATE SQL CONDITION OF 'cSubscrbr' COLUMN FOR FILTERING OF CLIENTS TO BE IMPORTED
        if(sim1 != null && sim2 != null){
            simCondition= "(cSubscrbr IN ("+loConstants.GetSimSubscriber(sim1)+","+loConstants.GetSimSubscriber(sim2)+"))";
        }else {
            if (sim1 != null){
                simCondition=  "(cSubscrbr = '"+loConstants.GetSimSubscriber(sim1)+"')";
            } else if (sim2 != null) {
                simCondition=  "(cSubscrbr = '"+loConstants.GetSimSubscriber(sim2)+"')";
            }
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
                    if (poDaoLeadCalls.SaveLeads(eLeadCalls).intValue() < 1){
                        message= "Failed to save leads on device";
                        Log.d(TAG, "Table: Call_Outgoing Transaction No: "+ sTransNox);
                        return false;
                    }
                }else {
                    if (poDaoLeadCalls.UpdateLeads(eLeadCalls) < 1){
                        message= "Failed to update leads on device";
                        Log.d(TAG, "Table: Call_Outgoing Transaction No: "+ sTransNox);
                        return false;
                    }
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
                    if (poDaoPriorities.SavePriorities(ePriorities).intValue() < 1){
                        message= "Failed to save priorities on device";
                        Log.d(TAG, "Table: Call_Priorities Index No: "+ String.valueOf(index));
                        return false;
                    }
                }else {
                    if (poDaoPriorities.UpdatePriorities(ePriorities) < 1){
                        message= "Failed to update leads on device";
                        Log.d(TAG, "Table: Call_Priorities Index No: "+ String.valueOf(index));
                        return false;
                    }
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
        this.cSubscr = loLeads.getsSubscr();
        this.sUserIDx = poSession.getUserID();
        this.dToday = frmDt;
    }
    public void InitCallTime(String sCallStrt, String sCallEnd) throws ParseException {
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
    public Boolean SaveClient2Call(){
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
                if (poDaoClient.SaveClients(eClient2Call).intValue() < 1){
                    message= "Failed to save client's information";
                    Log.d(TAG, "Table: Client_Master Client ID: "+ sClientid);
                    return false;
                }
            }else {
                if (poDaoClient.UpdateClients(eClient2Call) < 1){
                    message= "Failed to update client's information";
                    Log.d(TAG, "Table: Client_Master Client ID: "+ sClientid);
                    return false;
                }
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
                if (poDaoClientMobile.SaveClientMobile(eClientMobile) < 1){
                    message = "Failed to import client's mobile information";
                    Log.d(TAG, "Table: Client_Mobile Mobile No: " + sMobile);
                    return false;
                }
            }else {
                if (poDaoClientMobile.UpdateClientMobile(eClientMobile) < 1){
                    message = sClientID + " client id failed to update on local";
                    Log.d(TAG, "Table: Client_Mobile Mobile No: " + sMobile);
                    return false;
                }
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
            emcInquiry.setnTerms(Integer.valueOf(loInq.get("nTerms").toString()));
            emcInquiry.setdTargetxx(loInq.get("dTargetxx").toString());
            emcInquiry.setnDownPaym(Double.valueOf(loInq.get("nDownPaym").toString()));
            emcInquiry.setnMonAmort(Double.valueOf(loInq.get("nMonAmort").toString()));
            emcInquiry.setnCashPrc(Double.valueOf(loInq.get("nCashPrc").toString()));
            emcInquiry.setsRelatnID(loInq.get("sRelatnID").toString());
            emcInquiry.setsTableNM(loInq.get("sTableNM").toString());

            if (poDaoMcInq.GetMCInquiry(sTransNox) == null){
                if (poDaoMcInq.SaveMCInq(emcInquiry).intValue() < 1){
                    message = "Failed to import inquiry on device";
                    Log.d(TAG, "Table: "+ loInq.get("sTableNM") + "Transaction No: " + sTransNox);
                    return false;
                }
            }else {
                if (poDaoMcInq.UpdateMCInq(emcInquiry) < 1){
                    message = "Failed to update inquiry on device";
                    Log.d(TAG, "Table: "+ loInq.get("sTableNM") + "Transaction No: " + sTransNox);
                    return false;
                }
            }

            message = "Inquiries saved to device";
            return true;
        }catch (Exception e){
            message = e.getMessage();
            return false;
        }
    }
    public Boolean SaveCallStatus(String sCallStat, String callAction, String sApprvCD){
        try {
            //validate first if transaction no is applied
            if (sTransNox == null){
                message = "No applied transaction number";
                return false;
            }

            //send json params for web request and get response
            JSONObject loTransParams = poTeleApp.SendCallStatus(sCallStat, callAction, sTransNox, cSubscr,
                    sApprvCD, sUserIDx, sClientID, sMobileNo, sCallStrt, sCallEnd);

            if (loTransParams == null){
                message = poTeleApp.getMessage();
                return false;
            }

            String dTransact = loTransParams.get("dTransact").toString();
            if (sCallStat == "POSSIBLE SALES"){

            }

            //'POSSIBLE SALES' AND 'NOT NOW' STATUS, INSERT TO HOTLINE_OUTGOING
            if (sCallStat == "POSSIBLE SALES" || sCallStat == "NOT NOW"){
                String sHOutgoingNox = loTransParams.get("sTransNox").toString();

                if (sHOutgoingNox != null && !sHOutgoingNox.isEmpty()){
                    InsertHotlineOutgoing(sCallStat, sHOutgoingNox,
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
                message= getMessage() + " Status: Unreachable";
            }
            //UPDATE CLIENT MOBILE, IMPORT DATA AS NEW ROW IF NOT FOUND ON LOCAL
            if (UpdateCMobile(sClientID, sMobileNo, dTransact, nUnreachx) == false){
                message = getMessage();
                return false;
            }

            //UPDATE LEAD'S CTLMSTATX TO SELECTED STATUS
            if (UpdateLeadCallStat(sTransNox, sCallStat, sApprvCD, callAction) == false){
                message = getMessage();
                return false;
            }

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

            JSONObject loSchedule =  poTeleApp.UploadSchedule(sReferNox, sLeadSrc, dFollowUp, cTranstat, sRemarks, sUserIDx);
            if (loSchedule == null){
                message = poTeleApp.getMessage();
                return false;
            }

            String loTransNox = loSchedule.get("sTransNox").toString();
            String loFollowUp = loSchedule.get("dFollowUp").toString();
            String loTableNm = loSchedule.get("tablenm").toString();

            if (poDaoMcInq.UpdateFollowUp(loFollowUp, loTransNox) < 1){
                message = "Failed to save schedule on device";
                Log.d(TAG, "Table: " + loTableNm + " Transaction No: " + loTransNox);
                return false;
            }

            message = "Schedule has been saved on device";
            return true;
        }catch (Exception e){
            message = e.getMessage();
            return false;
        }
    }
    public Boolean InsertHotlineOutgoing(String sCallstat, String sTransNox, String dTransact,String sDivision, String sMobileNo,
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
            if (poDaoHOutgoing.SaveHotlineOutgoing(eHotlineOutgoing) < 1){
                message= "Failed to save "+ sCallstat + " transaction on device";
                Log.d(TAG, "Table: Hotline_Outgoing Transaction No: " + sTransNox);
                return false;
            }
        }else {
            if (poDaoHOutgoing.UpdateHotlineOutgoing(eHotlineOutgoing) < 1){
                message= "Failed to update "+ sCallstat + " transaction on device";
                Log.d(TAG, "Table: Hotline_Outgoing Transaction No: " + sTransNox);
                return false;
            }
        }

        message = "Transaction has been saved to device";
        Log.d(TAG, "Table: Hotline_Outgoing Transaction No: " + sTransNox);
        return true;
    }
    public Boolean UpdateCMobile(String sClientID, String sMobileNo, String dTransact, int nUnreachx){

        if (poDaoClientMobile.UpdateCallTrans(sClientID, sMobileNo, dTransact, nUnreachx) < 1){
            message= "Client Mobile failed to update on device";
            Log.d(TAG, "Table: Client Mobile Mobile No: " + sMobileNo);
            return false;
        }

        message = "Client Mobile has been update on device";
        Log.d(TAG, "Table: Client Mobile Mobile No: " + sMobileNo);
        return true;
    }
    public Boolean UpdateLeadCallStat(String sTransNox, String sCallStat, String sApprvCd, String cTransTat){
        if (poDaoLeadCalls.UpdateLeadCall(sTransNox, loConstants.GetRemarks(sCallStat), cTransTat, sApprvCd, sCallStrt,
                sCallEnd, sUserIDx, dToday) < 1){
            message= "Lead transaction failed to update on device";
            Log.d(TAG, "Table: Call_Outgoing Transaction No: " + sTransNox);
            return false;
        }

        message= "Lead transaction has been updated to device";
        Log.d(TAG, "Table: Call_Outgoing Transaction No: " + sTransNox);
        return true;
    }
    public LiveData<DAOLeadCalls.LeadInformation> GetLeadQueues(){
        return poDaoLeadCalls.GetInitLead(poSession.getUserID(), sim1, sim2);
    }
    public LiveData<DAOLeadCalls.LeadDetails> GetLeadDetails(){ //ON QUEUES, PRIORITY CALLS
        return poDaoLeadCalls.GetLeadDetails(sReferNox);
    }
    public LiveData<List<DAOLeadCalls.LeadHistory>> GetHistory(){
        return poDaoLeadCalls.GetCallHistory();
    }
}
