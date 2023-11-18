package org.rmj.g3appdriver.GCircle.Apps.TeleMarketing;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
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
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClientMobile;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EHotline_Outgoing;
import org.rmj.g3appdriver.lib.Telemarketing.entities.ELeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EMCInquiry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        this.poDaoClient = GGC_GCircleDB.getInstance(instance).teleCallClientsDao();
        this.poDaoClientMobile = GGC_GCircleDB.getInstance(instance).teleClientMobDao();
        this.poDaoMcInq = GGC_GCircleDB.getInstance(instance).teleMCInquiryDao();
        this.poDaoHOutgoing = GGC_GCircleDB.getInstance(instance).teleHOutgoingDao();

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
    public Boolean ImportCalls(){ //IMPORT LEADS WITH CLIENT INFO, PRODUCT INQUIRY
        try {
            JSONArray loArray = poTeleApp.GetLeads(poSession.getUserID(), CreateSubscrCondition());
            if (loArray == null){
                message = poTeleApp.getMessage();
                return false;
            }

            List<String> ListClientInfos = new ArrayList<>(); //list of params for client info
            HashMap<String, String> HashClientMobile = new HashMap<>(); //list of params for client mobile
            HashMap<String, String> HashMCInq = new HashMap<>(); //list of params for inquiries

            //ITERATE ROWS FROM JSON ARRAY
            for (int i = 0; i < loArray.length(); i++){

                JSONObject jsonLeads = loArray.getJSONObject(i).getJSONObject("leads");//convert each array to json object
                String sTransNox = jsonLeads.get("sTransNox").toString();

                //INITIALIZE ENTITY COLUMNS
                ELeadCalls eLeadCalls = new ELeadCalls();
                eLeadCalls.setsTransNox(sTransNox);
                eLeadCalls.setsAgentIDx(jsonLeads.get("sAgentIDx").toString());
                eLeadCalls.setdTransact(jsonLeads.get("dTransact").toString());
                eLeadCalls.setsClientID(jsonLeads.get("sClientID").toString());
                eLeadCalls.setsMobileNo(jsonLeads.get("sMobileNo").toString());
                eLeadCalls.setsRemarksx(jsonLeads.get("sRemarksx").toString());
                eLeadCalls.setsReferNox(jsonLeads.get("sReferNox").toString());
                eLeadCalls.setsSourceCD(jsonLeads.get("sSourceCD").toString());
                eLeadCalls.setsApprovCd(jsonLeads.get("sApprovCd").toString());
                eLeadCalls.setcTranStat(jsonLeads.get("cTranStat").toString());
                eLeadCalls.setdCallStrt(jsonLeads.get("dCallStrt").toString());
                eLeadCalls.setdCallEndx(jsonLeads.get("dCallEndx").toString());
                eLeadCalls.setnNoRetryx(Integer.valueOf(jsonLeads.get("nNoRetryx").toString()));
                eLeadCalls.setcSubscrbr(jsonLeads.get("cSubscrbr").toString());
                eLeadCalls.setcCallStat(jsonLeads.get("cCallStat").toString());
                eLeadCalls.setcTLMStatx(jsonLeads.get("cTLMStatx").toString());
                eLeadCalls.setcSMSStatx(jsonLeads.get("cSMSStatx").toString());
                eLeadCalls.setnSMSSentx(Integer.valueOf(jsonLeads.get("nSMSSentx").toString()));
                eLeadCalls.setsModified(jsonLeads.get("sModified").toString());
                eLeadCalls.setdModified(jsonLeads.get("dModified").toString());

                String sClientId = jsonLeads.get("sClientID").toString();
                String sMobile = jsonLeads.get("sMobileNo").toString();
                String sLeadSrc = jsonLeads.get("sSourceCD").toString();

                //GET EXISTING RECORD ON LOCAL DB, IF 0 'SAVE' ELSE 'UPDATE'
                if (poDaoLeadCalls.GetLeadTrans(sTransNox) == null){
                    if (poDaoLeadCalls.SaveLeads(eLeadCalls).intValue() < 1){
                        message= "Failed to import leads on device";
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

                //Collect all datas related to leads
                if (!sClientId.isEmpty()){ //client id not empty
                    ListClientInfos.add(sClientId);

                    if (!sMobile.isEmpty()){ //mobile no not empty
                        HashClientMobile.put(sMobile, sClientId);
                    }
                }

                if (!sTransNox.isEmpty() && !sLeadSrc.isEmpty()){
                    HashMCInq.put(sTransNox, sLeadSrc);
                }
            }
            if (ImportClient2Call(ListClientInfos) == false){
                message = getMessage();
                return false;
            }
            if (ImportClientMobile(HashClientMobile) == false){
                message = getMessage();
                return false;
            }
            if (ImportInquiries(HashMCInq) == false){
                message = getMessage();
                return false;
            }

            message= "Lead Calls imported successfully to device";

            return true;
        }catch (JSONException e){
            message= e.getMessage();
            return false;
        }
    }
    public Boolean ImportClient2Call(List<String> ClientIDParams){
        try {
            if (ClientIDParams.size() < 1){
                message = "No client informations to import";
                return false;
            }

            for (int i = 0; i < ClientIDParams.size(); i++){
                JSONObject loClient = poTeleApp.GetClients(ClientIDParams.get(i));

                if (loClient == null){
                    message = poTeleApp.getMessage();
                    return false;
                }

                String sClientid = loClient.get("sClientID").toString();

                //initialize entity column values
                EClient2Call eClient2Call = new EClient2Call();
                eClient2Call.setsClientID(sClientid);
                eClient2Call.setsClientNM(loClient.get("sCompnyNm").toString());
                eClient2Call.setxAddressx(loClient.get("xAddressx").toString());
                eClient2Call.setsMobileNox(loClient.get("sMobileNo").toString());
                eClient2Call.setsPhoneNox(loClient.get("sPhoneNox").toString());

                //get exisitng record on local database, if 0 then insert else update
                if (poDaoClient.GetClient2Call(sClientid) == null){
                    if (poDaoClient.SaveClients(eClient2Call).intValue() < 1){
                        message= "Failed to import client's information";
                        Log.d(TAG, "Table: Client_Master Client ID: "+ sClientid);
                        return false;
                    }
                }else {
                    if (poDaoClient.UpdateClients(eClient2Call) < 1){
                        message= "Failed to import client's information";
                        Log.d(TAG, "Table: Client_Master Client ID: "+ sClientid);
                        return false;
                    }
                }
            }
            message = "Client's Info imported successfully to device";
            return true;
        }catch (Exception e){
            message = e.getMessage();
            return false;
        }
    }
    public Boolean ImportClientMobile(HashMap<String, String> ClientMobParams){
        try {
            if (ClientMobParams.size() < 1){
                message = "No client's mobile information to import";
                return false;
            }

            for (Map.Entry<String, String> params: ClientMobParams.entrySet()){
                String sMobile = params.getKey();
                String sClientId = params.getValue();

                JSONObject loMobile = poTeleApp.GetCLientMobile(sClientId,sMobile);
                if (loMobile == null){
                    message = poTeleApp.getMessage();
                }

                EClientMobile eClientMobile = new EClientMobile();

                eClientMobile.setsClientID(loMobile.get("sClientID").toString());
                eClientMobile.setnEntryNox(Integer.valueOf(loMobile.get("nEntryNox").toString()));
                eClientMobile.setsMobileNo(loMobile.get("sMobileNo").toString());
                eClientMobile.setnPriority(Integer.valueOf(loMobile.get("nPriority").toString()));
                eClientMobile.setcIncdMktg(loMobile.get("cIncdMktg").toString());
                eClientMobile.setnUnreachx(Integer.valueOf(loMobile.get("nUnreachx").toString()));
                eClientMobile.setdLastVeri(loMobile.get("dLastVeri").toString());
                eClientMobile.setdInactive(loMobile.get("dInactive").toString());
                eClientMobile.setnNoRetryx(Integer.valueOf(loMobile.get("nNoRetryx").toString()));
                eClientMobile.setcInvalidx(loMobile.get("cInvalidx").toString());
                eClientMobile.setsIdleTime(loMobile.get("sIdleTime").toString());
                eClientMobile.setcConfirmd(loMobile.get("cConfirmd").toString());
                eClientMobile.setdConfirmd(loMobile.get("dConfirmd").toString());
                eClientMobile.setcSubscr(loMobile.get("cSubscrbr").toString());
                eClientMobile.setdHoldMktg(loMobile.get("dHoldMktg").toString());
                eClientMobile.setdLastCall(loMobile.get("dLastCall").toString());
                eClientMobile.setcRecdStat(loMobile.get("cRecdStat").toString());

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
            }

            message = "Client's Mobile imported successfully to device";
            return true;
        }catch (Exception e){
            message = e.getMessage();
            return false;
        }
    }
    public Boolean ImportInquiries(HashMap<String, String> InqParams){
        try {
            if (InqParams.size() < 1){
                message = "No inquiries to import";
                return false;
            }

            for (Map.Entry<String, String> params: InqParams.entrySet()){
                String sTransNox = params.getKey();
                String sLeadsrc = params.getValue();

                JSONObject loInq = poTeleApp.GetMCInquiries(sTransNox,sLeadsrc);
                if (loInq == null){
                    message = poTeleApp.getMessage();
                }

                //initialize entity column values
                EMCInquiry emcInquiry = new EMCInquiry();

                emcInquiry.setsTransNox(loInq.get("sTransNox").toString());
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
                        Log.d(TAG, "Table: "+ loInq.get("sTableNM").toString() + "Transaction No: " + sTransNox);
                        return false;
                    }
                }else {
                    if (poDaoMcInq.UpdateMCInq(emcInquiry) < 1){
                        message = "Failed to update inquiry on device";
                        Log.d(TAG, "Table: "+ loInq.get("sTableNM").toString() + "Transaction No: " + sTransNox);
                        return false;
                    }
                }
            }

            message = "Inquiries imported successfully to device";
            return true;
        }catch (Exception e){
            message = e.getMessage();
            return false;
        }
    }

    /* REQUIRED: NEED TO INITIALIZE FIRST TRANSACTION NO, BEFORE SAVING/UPDATING TRANSACTIONS*/
    public void InitTransaction(String sTransNox){
        ELeadCalls leadCalls = poDaoLeadCalls.GetLeadTrans(sTransNox);

        this.sTransNox = sTransNox;
        this.sLeadSrc = leadCalls.getsSourceCD();
        this.sClientID = leadCalls.getsClientID();
        this.sMobileNo = leadCalls.getsMobileNo();
        this.cSubscr = leadCalls.getcSubscrbr();
        this.sUserIDx = poSession.getUserID();
    }
    public Boolean SaveCallStatus(String sCallStat, String sApprvCD){
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
            if (loTransParams == null){
                message = poTeleApp.getMessage();
                return false;
            }

            String dTransact = loTransParams.get("dTransact").toString();

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
            if (UpdateLeadCallStat(sTransNox, sCallStat) == false){
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
    public Boolean UpdateLeadCallStat(String sTransNox, String sCallStat){
        if (poDaoLeadCalls.UpdateLeadCall(sTransNox, loConstants.GetRemarks(sCallStat)) < 1){
            message= "Lead transaction failed to update on device";
            Log.d(TAG, "Table: Call_Outgoing Transaction No: " + sTransNox);
            return false;
        }

        message= "Lead transaction has been updated to device";
        Log.d(TAG, "Table: Call_Outgoing Transaction No: " + sTransNox);
        return true;
    }
    public Boolean SaveSchedule(String dFollowUp, String cTranstat, String sRemarks){
        try {
            if (sTransNox == null){
                message = "No applied transaction no";
                return false;
            }

            if (dFollowUp == null){
                message = "No schedule date applied";
                return false;
            }

            JSONObject loSchedule =  poTeleApp.UploadSchedule(sTransNox, sLeadSrc, dFollowUp, cTranstat, sRemarks, sUserIDx);
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
