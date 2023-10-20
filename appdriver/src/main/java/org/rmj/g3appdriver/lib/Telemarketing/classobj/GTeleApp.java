package org.rmj.g3appdriver.lib.Telemarketing.classobj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;

import android.app.Application;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.HttpHeaderProvider;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOClientMobile;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOLeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOMCInquiry;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClientMobile;
import org.rmj.g3appdriver.lib.Telemarketing.entities.ELeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EMCInquiry;
public class GTeleApp {
    private String TAG = getClass().getSimpleName();
    private String message;
    private GCircleApi loApi;
    private HttpHeaderProvider poHeader;
    private DAOLeadCalls poDaoLeads;
    private DAOClient2Call poDaoClient;
    private DAOMCInquiry poDaoMcInq;
    private DAOClientMobile poDaoClientMobile;
    public GTeleApp(Application instance){
        this.loApi = new GCircleApi(instance);
        this.poHeader = HttpHeaderManager.getInstance(instance).initializeHeader();
        this.poDaoLeads = GGC_GCircleDB.getInstance(instance).teleLeadsDao();
        this.poDaoClient = GGC_GCircleDB.getInstance(instance).teleCallClientsDao();
        this.poDaoMcInq = GGC_GCircleDB.getInstance(instance).teleMCInquiryDao();
        this.poDaoClientMobile = GGC_GCircleDB.getInstance(instance).teleClientMobDao();
    }
    public String getMessage(){
        return message;
    }
    private boolean ImportClients(String sClientID){
        try {
            //create params on http using json object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sClientID", sClientID);

            //send params and get result
            String loResponse = WebClient.sendRequest(loApi.getURLClientCalls(),
                    jsonObject.toString(), poHeader.getHeaders());

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
            if (poDaoClient.GetClientCall(sClientID) == null){
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
    private boolean ImportMCInquiries(String sTransNox, String sLeadSrc){
        try {
            //create params on http using json object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sTransNox", sTransNox);
            jsonObject.put("sLeadSrc", sLeadSrc);

            //send params and get result
            String loResponse = WebClient.sendRequest(loApi.getURLMCInq(),
                    jsonObject.toString(), poHeader.getHeaders());

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
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sClientID", sClientID);
            jsonObject.put("sMobileNo", sMobileNo);

            //SEND PARAMS AND GET RESULT
            String loResponse = WebClient.sendRequest(loApi.getURLLeadCalls(),
                    jsonObject.toString(), poHeader.getHeaders());
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
            Log.d(TAG, e.getMessage());
            return false;
        }
    }
    public boolean ImportLeads(String sUserID, String simSubscr){
        try {
            //CREATE PARAMS USING JSON OBJECT
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sAgentIDx", sUserID);
            jsonObject.put("cSubscrbr", simSubscr);

            //SEND PARAMS AND GET RESULT
            String loResponse = WebClient.sendRequest(loApi.getURLLeadCalls(),
                                                        jsonObject.toString(), poHeader.getHeaders());
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
            JSONArray loArray = loJson.getJSONArray("leads");

            //ITERATE ROWS FROM RESPONSE
            for (int i = 0; i < loArray.length(); i++){
                JSONObject loResult = loArray.getJSONObject(i);

                String stransNox = loResult.getString("sTransNox");

                //INITIALIZE ENTITY COLUMNS
                ELeadCalls eLeadCalls = new ELeadCalls();
                eLeadCalls.setsTransNox(stransNox);
                eLeadCalls.setsAgentIDx(loResult.getString("sAgentIDx"));
                eLeadCalls.setdTransact(loResult.getString("dTransact"));
                eLeadCalls.setsClientID(loResult.getString("sClientID"));
                eLeadCalls.setsMobileNo(loResult.getString("sMobileNo"));
                eLeadCalls.setsRemarksx(loResult.getString("sRemarksx"));
                eLeadCalls.setsReferNox(loResult.getString("sReferNox"));
                eLeadCalls.setsSourceCD(loResult.getString("sSourceCD"));
                eLeadCalls.setsApprovCd(loResult.getString("sApprovCd"));
                eLeadCalls.setcTranStat(loResult.getString("cTranStat"));
                eLeadCalls.setdCallStrt(loResult.getString("dCallStrt"));
                eLeadCalls.setdCallEndx(loResult.getString("dCallEndx"));
                eLeadCalls.setnNoRetryx(loResult.getInt("nNoRetryx"));
                eLeadCalls.setcSubscrbr(loResult.getInt("cSubscrbr"));
                eLeadCalls.setcCallStat(loResult.getInt("cCallStat"));
                eLeadCalls.setcTLMStatx(loResult.getInt("cTLMStatx"));
                eLeadCalls.setcSMSStatx(loResult.getInt("cSMSStatx"));
                eLeadCalls.setnSMSSentx(loResult.getInt("nSMSSentx"));
                eLeadCalls.setsModified(loResult.getString("sModified"));
                eLeadCalls.setdModified(loResult.getString("dModified"));

                String sTransNox = loResult.getString("sTransNox");
                String sClientId = loResult.getString("sClientID");
                String sLeadSrc = loResult.getString("sSourceCD");
                String sMobile = loResult.getString("sMobileNo");

                //GET EXISTING RECORD ON LOCAL DB, IF 0 'SAVE' ELSE 'UPDATE'
                if (poDaoLeads.CountLeadTrans(stransNox) < 1){
                    poDaoLeads.SaveLeads(eLeadCalls);
                    Log.d(TAG,sTransNox+" has been saved to local");
                }else {
                    poDaoLeads.UpdateLeads(eLeadCalls);
                    Log.d(TAG, sTransNox+" has been updated to local");
                }

                //CALL METHOD TO IMPORT CLIENT, INQUIRiES, MOBILE INFO BASED ON RETURNED LEADS
                if (ImportClients(sClientId) == false){
                    return false;
                }
                if (ImportMCInquiries(sTransNox, sLeadSrc) == false){
                    return false;
                }
                if (ImportCLientMobile(sClientId, sMobile) == false){
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return false;
        }
    }
}
