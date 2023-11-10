package org.rmj.g3appdriver.lib.Telemarketing.classobj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.HttpHeaderProvider;
import org.rmj.g3appdriver.dev.Http.WebClient;
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
public class GTeleApp {
    private String TAG = getClass().getSimpleName();
    private String message;
    private GCircleApi loApi;
    private HttpHeaderProvider poHeader;
    private GTeleConstants loConstants;
    private DAOLeadCalls poDaoLeads;
    private DAOClient2Call poDaoClient;
    private DAOMCInquiry poDaoMcInq;
    private DAOClientMobile poDaoClientMobile;
    private DAOHoutlineOutgoing poDaoHOutgoing;
    public GTeleApp(Application instance){
        this.loApi = new GCircleApi(instance);
        this.poHeader = HttpHeaderManager.getInstance(instance).initializeHeader();
        this.loConstants = new GTeleConstants();
        this.poDaoLeads = GGC_GCircleDB.getInstance(instance).teleLeadsDao();
        this.poDaoClient = GGC_GCircleDB.getInstance(instance).teleCallClientsDao();
        this.poDaoMcInq = GGC_GCircleDB.getInstance(instance).teleMCInquiryDao();
        this.poDaoClientMobile = GGC_GCircleDB.getInstance(instance).teleClientMobDao();
        this.poDaoHOutgoing = GGC_GCircleDB.getInstance(instance).teleHOutgoingDao();
    }
    public String getMessage(){
        return message;
    }
    public boolean ImportLeads(String sUserID, String simSubscr){
        try {
            //CREATE PARAMS USING JSON OBJECT
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("sAgentIDx", sUserID);
            jsonParam.put("cSubscrbr", simSubscr);

            //SEND PARAMS AND GET RESULT
            String loResponse = WebClient.sendRequest(loApi.getURLLeadCalls(),
                    jsonParam.toString(), poHeader.getHeaders());
            if(loResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }
            //CONVERT WEB SERVER RESPONSE TO JSON
            JSONObject loJson = new JSONObject(loResponse);

            //GET ERROR RETURNED FROM SERVER
            String lsResult = loJson.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loJson.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            //CONVERT JSON RESPONSE TO JSON ARRAY
            JSONArray loArray = loJson.getJSONArray("leadsload");

            //ITERATE ROWS FROM RESPONSE
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
                String sLeadSrc = jsonLeads.get("sSourceCD").toString();
                String sMobile = jsonLeads.get("sMobileNo").toString();

                //GET EXISTING RECORD ON LOCAL DB, IF 0 'SAVE' ELSE 'UPDATE'
                if (poDaoLeads.GetLeadTrans(sTransNox) == null){
                    if (poDaoLeads.SaveLeads(eLeadCalls).intValue() < 1){
                        message= sTransNox+" not saved to local";
                        return false;
                    }
                    message= sTransNox+" has been saved to local";
                }else {
                    if (poDaoLeads.UpdateLeads(eLeadCalls) < 1){
                        message= sTransNox+" not updated to local";
                        return false;
                    }
                    message= sTransNox+" has been updated to local";
                }

                //CALL METHOD TO IMPORT CLIENT, INQUIRiES, MOBILE INFO BASED ON RETURNED LEADS
                if (ImportClients(sClientId) == false){
                    return false;
                }
                if (ImportCLientMobile(sClientId, sMobile) == false){
                    return false;
                }
                if (ImportMCInquiries(sTransNox, sLeadSrc) == false){
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            message= e.getMessage();
            return false;
        }
    }
    public boolean ImportClients(String sClientID){
        try {
            //create params on http using json object
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("sClientID", sClientID);

            //send params and get result
            String loResponse = WebClient.sendRequest(loApi.getURLClientCalls(),
                    jsonParam.toString(), poHeader.getHeaders());

            //convert web server response to json object
            JSONObject loJson = new JSONObject(loResponse);

            //get error returned from web response
            String lsResult = loJson.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loJson.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            //convert json object response to json array for multiple results
            JSONObject loClient = loJson.getJSONObject("clientinfo");

            //initialize entity column values
            EClient2Call eClient2Call = new EClient2Call();
            eClient2Call.setsClientID(loClient.get("sClientID").toString());
            eClient2Call.setsClientNM(loClient.get("sCompnyNm").toString());
            eClient2Call.setxAddressx(loClient.get("xAddressx").toString());
            eClient2Call.setsMobileNox(loClient.get("sMobileNo").toString());
            eClient2Call.setsPhoneNox(loClient.get("sPhoneNox").toString());

            //get exisitng record on local database, if 0 then insert else update
            if (poDaoClient.GetClient2Call(sClientID) == null){
                poDaoClient.SaveClients(eClient2Call);
            }else {
                poDaoClient.UpdateClients(eClient2Call);
            }
            return true;
        } catch (Exception e) {
            message = e.getMessage();
            return false;
        }
    }
    public boolean ImportMCInquiries(String sTransNox, String sLeadSrc){
        try {
            //create params on http using json object
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("sTransNox", sTransNox);
            jsonParam.put("sLeadSrc", sLeadSrc);

            //send params and get result
            String loResponse = WebClient.sendRequest(loApi.getURLMCInq(),
                    jsonParam.toString(), poHeader.getHeaders());

            //convert web server response to json object
            JSONObject loJson = new JSONObject(loResponse);

            //get error returned from web response
            String lsResult = loJson.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loJson.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            //convert json object response to json array for multiple results
            JSONObject loInq = loJson.getJSONObject("mcinq");

            //initialize entity column values
            EMCInquiry emcInquiry = new EMCInquiry();

            emcInquiry.setsTransNox(loInq.getString("sTransNox"));
            emcInquiry.setdFollowUp(loInq.getString("dFollowUp"));
            emcInquiry.setsClientID(loInq.getString("sClientID"));
            emcInquiry.setsBrandIDx(loInq.getString("sBrandIDx"));
            emcInquiry.setsModelIDx(loInq.getString("sModelIDx"));
            emcInquiry.setsColorIDx(loInq.getString("sColorIDx"));
            emcInquiry.setnTerms(loInq.getInt("nTerms"));
            emcInquiry.setdTargetxx(loInq.getString("dTargetxx"));
            emcInquiry.setnDownPaym(loInq.getDouble("nDownPaym"));
            emcInquiry.setnMonAmort(loInq.getDouble("nMonAmort"));
            emcInquiry.setnCashPrc(loInq.getDouble("nCashPrc"));
            emcInquiry.setsRelatnID(loInq.getString("sRelatnID"));
            emcInquiry.setsTableNM(loInq.getString("sTableNM"));

            if (poDaoMcInq.GetMCInquiry(sTransNox) == null){
                poDaoMcInq.SaveMCInq(emcInquiry);
            }else {
                poDaoMcInq.UpdateMCInq(emcInquiry);
            }
            return true;
        } catch (Exception e) {
            message = e.getMessage();
            return false;
        }
    }
    public boolean ImportCLientMobile(String sClientID, String sMobileNo){
        try {
            //CREATE PARAMS USING JSON OBJECT
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("sClientID", sClientID);
            jsonParam.put("sMobileNo", sMobileNo);

            //SEND PARAMS AND GET RESULT
            String loResponse = WebClient.sendRequest(loApi.getURLLeadCalls(),
                    jsonParam.toString(), poHeader.getHeaders());
            if(loResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }
            //CONVERT WEB SERVER RESPONSE TO JSON
            JSONObject loJson = new JSONObject(loResponse);

            //GET ERROR RETURNED FROM SERVER
            String lsResult = loJson.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loJson.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            //CONVERT JSON RESPONSE TO JSON ARRAY
            //convert json object response to json array for multiple results
            JSONObject loInq = loJson.getJSONObject("client_mobile");

            EClientMobile eClientMobile = new EClientMobile();

            eClientMobile.setsClientID(loInq.get("sClientID").toString());
            eClientMobile.setnEntryNox(Integer.valueOf(loInq.get("nEntryNox").toString()));
            eClientMobile.setsMobileNo(loInq.get("sMobileNo").toString());
            eClientMobile.setnPriority(Integer.valueOf(loInq.get("nPriority").toString()));
            eClientMobile.setcIncdMktg(loInq.get("cIncdMktg").toString());
            eClientMobile.setnUnreachx(Integer.valueOf(loInq.get("nUnreachx").toString()));
            eClientMobile.setdLastVeri(loInq.get("dLastVeri").toString());
            eClientMobile.setdInactive(loInq.get("dInactive").toString());
            eClientMobile.setnNoRetryx(Integer.valueOf(loInq.get("nNoRetryx").toString()));
            eClientMobile.setcInvalidx(loInq.get("cInvalidx").toString());
            eClientMobile.setsIdleTime(loInq.get("sIdleTime").toString());
            eClientMobile.setcConfirmd(loInq.get("cConfirmd").toString());
            eClientMobile.setdConfirmd(loInq.get("dConfirmd").toString());
            eClientMobile.setcSubscr(loInq.get("cSubscrbr").toString());
            eClientMobile.setdHoldMktg(loInq.get("dHoldMktg").toString());
            eClientMobile.setdLastCall(loInq.get("dLastCall").toString());
            eClientMobile.setcRecdStat(loInq.get("cRecdStat").toString());

            if (poDaoClientMobile.GetClientMobile(sClientID, sMobileNo) == null){
                poDaoClientMobile.SaveClientMobile(eClientMobile);
            }else {
                poDaoClientMobile.UpdateClientMobile(eClientMobile);
            }
            return true;
        } catch (Exception e) {
            message= e.getMessage();
            return false;
        }
    }
    public ELeadCalls GetLeadTrans(String sTransNox){
        ELeadCalls eLeadCalls = poDaoLeads.GetLeadTrans(sTransNox);
        return eLeadCalls;
    }
    public boolean UploadSchedule(String sTransNox, String sLeadsrc, String dFollowUp, String cTranstat,
                                  String sRemarks, String sUserIDx){
        try{
            //CREATE PARAMS USING JSON OBJECT
            JSONObject jsonParam = new JSONObject();

            jsonParam.put("sTransNox", sTransNox);
            jsonParam.put("sLeadSrc", loConstants.GetLeadConstant(sLeadsrc));
            jsonParam.put("dFollowUp", dFollowUp);
            jsonParam.put("cTransStat", cTranstat);
            jsonParam.put("sRemarks", sRemarks);
            jsonParam.put("sUserID", sUserIDx);

            String lsResponse = WebClient.sendRequest(
                    loApi.getUrlSendSchedule(),
                    jsonParam.toString(),
                    poHeader.getHeaders());

            if (lsResponse == null) {
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);;
                return false;
            }

            if (loResponse.getString("sTransNox") == null){
                message = "No transaction no returned from server";
                return false;
            }

            poDaoMcInq.UpdateFollowUp(dFollowUp, loResponse.getString("sTransNox"));
            message = "Schedule has been updated!";
            return true;
        }catch (Exception e){
            message = e.getMessage();
            return false;
        }
    }
    /*THESE METHOD UPLOADS ALL CALL TRANSACTIONS AND STATUS AT ONCE*/
    public JSONObject SendCallStatus(String sCallStat, String sReferNox, String cSubscr, String sApprvCd, String sUserID,
                        String sClientID, String sMobileNo){
        try {
            //CREATE PARAMS USING JSON OBJECT
            JSONObject jsonParam = new JSONObject();

            //PARAM FOR CALL_OUTGOING UPDATE
            jsonParam.put("sCallStat", loConstants.GetRemarks(sCallStat));
            jsonParam.put("sReferNox", sReferNox);

            //PARAM FOR HOTLINE OUTGOING INSERT
            jsonParam.put("cSubscr", cSubscr);
            jsonParam.put("sApprvCd", sApprvCd);
            jsonParam.put("sUserID", sUserID);

            //PARAM FOR CLIENT MOBILE UPDATE
            jsonParam.put("sClientID", sClientID);
            jsonParam.put("sMobileNo", sMobileNo);

            String lsResponse = WebClient.sendRequest(
                    loApi.getUrlSendCallStatus(),
                    jsonParam.toString(),
                    poHeader.getHeaders());

            if (lsResponse == null) {
                message = SERVER_NO_RESPONSE;
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);;
                return null;
            }

            JSONObject loTransData = loResponse.getJSONObject("transactdata");
            return loTransData;
        }catch (Exception e){
            message = e.getMessage();
            return null;
        }
    }
    public void InsertHotlineOutgoing(String sTransNox, String dTransact,String sDivision, String sMobileNo,
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
            message= sTransNox+" has been saved to local";
        }else {
            poDaoHOutgoing.UpdateHotlineOutgoing(eHotlineOutgoing);
            message= sTransNox+" has been updated to local";
        }

    }
    public void UpdateCMobile(String sClientID, String sMobileNo, String dTransact, int nUnreachx){
        poDaoClientMobile.UpdateCallTrans(sClientID, sMobileNo, dTransact, nUnreachx);
        message= sClientID+" has been updated to local.";
    }
    public void UpdateLeadCallStat(String sTransNox, String sCallStat){
        poDaoLeads.UpdateLeadCall(sTransNox, loConstants.GetRemarks(sCallStat));
        message= sTransNox+" has been updated to local.";
    }
}
