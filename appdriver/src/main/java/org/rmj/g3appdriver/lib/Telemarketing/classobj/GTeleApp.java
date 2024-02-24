package org.rmj.g3appdriver.lib.Telemarketing.classobj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.HttpHeaderProvider;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.lib.Telemarketing.constants.GTeleConstants;

import java.util.List;

public class GTeleApp {
    private String TAG = getClass().getSimpleName();
    private String message;
    private GCircleApi loApi;
    private HttpHeaderProvider poHeader;
    private GTeleConstants loConstants;
    public GTeleApp(Application instance){
        this.loApi = new GCircleApi(instance);
        this.poHeader = HttpHeaderManager.getInstance(instance).initializeHeader();
        this.loConstants = new GTeleConstants();
    }
    public String getMessage(){
        return message;
    }
    public JSONArray GetLeads(String sUserID, String simSubscr){
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
                return null;
            }
            //CONVERT WEB SERVER RESPONSE TO JSON
            JSONObject loJson = new JSONObject(loResponse);

            //GET ERROR RETURNED FROM SERVER
            String lsResult = loJson.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loJson.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            //CONVERT JSON RESPONSE TO JSON ARRAY
            JSONArray loLeads = loJson.getJSONArray("initleads");
            return loLeads;
        } catch (Exception e) {
            message= e.getMessage();
            return null;
        }
    }
    public JSONArray GetPriorities(){
        try {
            //SEND PARAMS AND GET RESULT
            String loResponse = WebClient.sendRequest(loApi.getUrlPriorities(),
                    new JSONObject().toString(), poHeader.getHeaders());
            if(loResponse == null){
                message = SERVER_NO_RESPONSE;
                return null;
            }
            //CONVERT WEB SERVER RESPONSE TO JSON
            JSONObject loJson = new JSONObject(loResponse);

            //GET ERROR RETURNED FROM SERVER
            String lsResult = loJson.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loJson.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            //CONVERT JSON RESPONSE TO JSON ARRAY
            JSONArray loPriorities = loJson.getJSONArray("initpriorities");
            return loPriorities;
        } catch (Exception e) {
            message= e.getMessage();
            return null;
        }
    }
    public JSONObject GetClients(JSONObject loParams){
        try {
            //send params and get result
            String loResponse = WebClient.sendRequest(loApi.getURLClientCalls(),
                    loParams.toString(), poHeader.getHeaders());

            if(loResponse == null){
                message = SERVER_NO_RESPONSE;
                return null;
            }

            //convert web server response to json object
            JSONObject loJson = new JSONObject(loResponse);

            //get error returned from web response
            String lsResult = loJson.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loJson.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            //convert json object response to json array for multiple results
            JSONObject loClientS = loJson.getJSONObject("clientinfo");
            return loClientS;
        } catch (Exception e) {
            message = e.getMessage();
            return null;
        }
    }
    public JSONObject GetCLientMobile(JSONObject loParams){
        try {
            //SEND PARAMS AND GET RESULT
            String loResponse = WebClient.sendRequest(loApi.getURLClientMobile(),
                    loParams.toString(), poHeader.getHeaders());

            if(loResponse == null){
                message = SERVER_NO_RESPONSE;
                return null;
            }

            //CONVERT WEB SERVER RESPONSE TO JSON
            JSONObject loJson = new JSONObject(loResponse);

            //GET ERROR RETURNED FROM SERVER
            String lsResult = loJson.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loJson.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            //CONVERT JSON RESPONSE TO JSON ARRAY
            //convert json object response to json array for multiple results
            JSONObject loMobileS = loJson.getJSONObject("clientmobile");
            return loMobileS;
        } catch (Exception e) {
            message= e.getMessage();
            return null;
        }
    }
    public JSONObject GetMCInquiries(JSONObject loParams){
        try {
            //send params and get result
            String loResponse = WebClient.sendRequest(loApi.getURLMCInq(),
                    loParams.toString(), poHeader.getHeaders());

            if(loResponse == null){
                message = SERVER_NO_RESPONSE;
                return null;
            }

            //convert web server response to json object
            JSONObject loJson = new JSONObject(loResponse);

            //get error returned from web response
            String lsResult = loJson.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loJson.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            //convert json object response to json array for multiple results
            JSONObject loInquiries = loJson.getJSONObject("inquiries");
            return loInquiries;
        } catch (Exception e) {
            message = e.getMessage();
            return null;
        }
    }
    public Boolean UpdateStatus(String sTransNox, String sUserID, String cTransStat){
        try {
            //CREATE PARAMS USING JSON OBJECT
            JSONObject jsonParam = new JSONObject();

            //PARAM FOR CALL_OUTGOING UPDATE COLUMN: cTLMStatx
            jsonParam.put("sTransNox", sTransNox);
            jsonParam.put("sUserID", sUserID);
            jsonParam.put("cTransStat", cTransStat);

            String lsResponse = WebClient.sendRequest(
                    loApi.getUrlCreateLead(),
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
                message = getErrorMessage(loError);
                return false;
            }

            return true;
        }catch (Exception e){
            message = e.getMessage();
            return false;
        }
    }
    /*THESE METHOD UPLOADS ALL CALL TRANSACTIONS AND STATUS AT ONCE*/
    public JSONObject SendCallStatus(String sCallStat, String callAction, String sReferNox, String cSubscr, String sApprvCd, String sUserID,
                                        String sClientID, String sMobileNo, String sCallStrt, String sCallEnd, String sRemarksx){
        try {
            //CREATE PARAMS USING JSON OBJECT
            JSONObject jsonParam = new JSONObject();

            //PARAM FOR CALL_OUTGOING UPDATE COLUMN: cTLMStatx
            jsonParam.put("sCallStat", sCallStat);
            jsonParam.put("sReferNox", sReferNox);
            jsonParam.put("dCallStrt", sCallStrt);
            jsonParam.put("dCallEndx", sCallEnd);
            jsonParam.put("sRemarksx", sRemarksx);

            //PARAM FOR HOTLINE OUTGOING INSERT TABLE
            jsonParam.put("cSubscr", cSubscr);
            jsonParam.put("sApprvCd", sApprvCd);
            jsonParam.put("sDivision", "TLM"); //temporary, which primary user for this app is telemarketing
            jsonParam.put("cSendStat", "0"); //temporary, which may change on future adjustment
            jsonParam.put("nNoRetryx", 0); //temporary, which may change on future adjustment
            jsonParam.put("sUDHeader", ""); //temporary, which may change on future adjustment
            jsonParam.put("cTranStat", callAction);
            jsonParam.put("nPriority", "1"); //temporary, which may change on future adjustment
            jsonParam.put("sUserID", sUserID);

            //PARAM FOR CLIENT MOBILE UPDATE COLUMNS: nUnreachx, dLastCall
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

            message = "Transaction has been uploaded to server";
            JSONObject transResponse = loResponse.getJSONObject("transparams");
            return transResponse;
        }catch (Exception e){
            message = e.getMessage();
            return null;
        }
    }
    public JSONObject UploadSchedule(String sTransNox, String sLeadsrc, String dFollowUp, String cTranstat,
                                  String sRemarks, String sUserIDx){
        try{
            //CREATE PARAMS USING JSON OBJECT
            JSONObject jsonParam = new JSONObject();

            jsonParam.put("sTransNox", sTransNox);
            jsonParam.put("sLeadSrc", sLeadsrc);
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
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");

            //store result to message variable
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            JSONObject loSchedule = loResponse.getJSONObject("schedule");
            return loSchedule;
        }catch (Exception e){
            message = e.getMessage();
            return null;
        }
    }
}
