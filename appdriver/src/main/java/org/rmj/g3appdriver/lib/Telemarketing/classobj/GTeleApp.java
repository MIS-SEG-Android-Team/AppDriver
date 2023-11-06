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

                JSONObject loResult = loArray.getJSONObject(i);//convert each array to json object

                JSONObject jsonLeads = new JSONObject(loResult.getString("leads")); //convert each result to json object

                String sTransNox = jsonLeads.getString("sTransNox");

                //INITIALIZE ENTITY COLUMNS
                ELeadCalls eLeadCalls = new ELeadCalls();
                eLeadCalls.setsTransNox(sTransNox);
                eLeadCalls.setsAgentIDx(jsonLeads.getString("sAgentIDx"));
                eLeadCalls.setdTransact(jsonLeads.getString("dTransact"));
                eLeadCalls.setsClientID(jsonLeads.getString("sClientID"));
                eLeadCalls.setsMobileNo(jsonLeads.getString("sMobileNo"));
                eLeadCalls.setsRemarksx(jsonLeads.getString("sRemarksx"));
                eLeadCalls.setsReferNox(jsonLeads.getString("sReferNox"));
                eLeadCalls.setsSourceCD(jsonLeads.getString("sSourceCD"));
                eLeadCalls.setsApprovCd(jsonLeads.getString("sApprovCd"));
                eLeadCalls.setcTranStat(jsonLeads.getString("cTranStat"));
                eLeadCalls.setdCallStrt(jsonLeads.getString("dCallStrt"));
                eLeadCalls.setdCallEndx(jsonLeads.getString("dCallEndx"));
                eLeadCalls.setnNoRetryx(jsonLeads.getInt("nNoRetryx"));
                eLeadCalls.setcSubscrbr(jsonLeads.getInt("cSubscrbr"));
                eLeadCalls.setcCallStat(jsonLeads.getString("cCallStat"));
                eLeadCalls.setcTLMStatx(jsonLeads.getString("cTLMStatx"));
                eLeadCalls.setcSMSStatx(jsonLeads.getInt("cSMSStatx"));
                eLeadCalls.setnSMSSentx(jsonLeads.getInt("nSMSSentx"));
                eLeadCalls.setsModified(jsonLeads.getString("sModified"));
                eLeadCalls.setdModified(jsonLeads.getString("dModified"));

                String sClientId = jsonLeads.getString("sClientID");
                String sLeadSrc = jsonLeads.getString("sSourceCD");
                String sMobile = jsonLeads.getString("sMobileNo");

                //GET EXISTING RECORD ON LOCAL DB, IF 0 'SAVE' ELSE 'UPDATE'
                if (poDaoLeads.GetLeadTrans(sTransNox) == null){
                    poDaoLeads.SaveLeads(eLeadCalls);
                    message= sTransNox+" has been saved to local";
                }else {
                    poDaoLeads.UpdateLeads(eLeadCalls);
                    message= sTransNox+" has been updated to local";
                }

                //CALL METHOD TO IMPORT CLIENT, INQUIRiES, MOBILE INFO BASED ON RETURNED LEADS
                /*if (ImportClients(sClientId) == false){
                    return false;
                }
                if (ImportMCInquiries(sTransNox, sLeadSrc) == false){
                    return false;
                }
                if (ImportCLientMobile(sClientId, sMobile) == false){
                    return false;
                }*/
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
            eClient2Call.setsClientNM(loClient.getString("sClientNM"));
            eClient2Call.setsClientNM(loClient.getString("xAddressx"));
            eClient2Call.setsClientNM(loClient.getString("sPhoneNox"));
            eClient2Call.setsClientNM(loClient.getString("sMobileNox"));

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

            eClientMobile.setsClientID(loInq.getString("sClientID"));
            eClientMobile.setnEntryNox(loInq.getInt("nEntryNox"));
            eClientMobile.setsMobileNo(loInq.getString("sMobileNo"));
            eClientMobile.setnPriority(loInq.getInt("nPriority"));
            eClientMobile.setcIncdMktg(loInq.getString("cIncdMktg"));
            eClientMobile.setnUnreachx(loInq.getInt("nUnreachx"));
            eClientMobile.setdLastVeri(loInq.getString("dLastVeri"));
            eClientMobile.setdInactive(loInq.getString("dInactive"));
            eClientMobile.setnNoRetryx(loInq.getInt("nNoRetryx"));
            eClientMobile.setcInvalidx(loInq.getString("cInvalidx"));
            eClientMobile.setsIdleTime(loInq.getString("sIdleTime"));
            eClientMobile.setcConfirmd(loInq.getString("cConfirmd"));
            eClientMobile.setdConfirmd(loInq.getString("dConfirmd"));
            eClientMobile.setcSubscr(loInq.getString("cSubscr"));
            eClientMobile.setdHoldMktg(loInq.getString("dHoldMktg"));
            eClientMobile.setdLastCall(loInq.getString("dLastCall"));
            eClientMobile.setcRecdStat(loInq.getString("cRecdStat"));

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
    public JSONObject SendCallStatus(String sCallStat, String sReferNox, String cSubscr, String sApprvCd, String sUserID,
                        String sClientID, String sMobileNo){
        try {
            //CREATE PARAMS USING JSON OBJECT
            JSONObject jsonParam = new JSONObject();

            jsonParam.put("sCallStat", loConstants.GetRemarks(sCallStat));
            jsonParam.put("sReferNox", sReferNox);
            jsonParam.put("cSubscr", cSubscr);
            jsonParam.put("sApprvCd", sApprvCd);
            jsonParam.put("sUserID", sUserID);
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

            return loResponse;
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
