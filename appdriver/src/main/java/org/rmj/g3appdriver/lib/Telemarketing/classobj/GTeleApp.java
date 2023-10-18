package org.rmj.g3appdriver.lib.Telemarketing.classobj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GCircle.Account.EmployeeSession;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.Apps.TeleMarketing.pojo.CallClient;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.HttpHeaderProvider;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOLeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.dao.DAOMCInquiry;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EClient2Call;
import org.rmj.g3appdriver.lib.Telemarketing.entities.ELeadCalls;
import org.rmj.g3appdriver.lib.Telemarketing.entities.EMCInquiry;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class GTeleApp {
    private String TAG = getClass().getSimpleName();
    private String message;
    private GCircleApi loApi;
    private HttpHeaderProvider poHeader;
    private DAOLeadCalls poDaoLeads;
    private DAOClient2Call poDaoClient;
    private DAOMCInquiry poDaoMcInq;
    public GTeleApp(Application instance){
        this.loApi = new GCircleApi(instance);
        this.poHeader = HttpHeaderManager.getInstance(instance).initializeHeader();
        this.poDaoLeads = GGC_GCircleDB.getInstance(instance).teleLeadsDao();
        this.poDaoClient = GGC_GCircleDB.getInstance(instance).teleCallClientsDao();
        this.poDaoMcInq = GGC_GCircleDB.getInstance(instance).teleMCInquiry();
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
    public boolean ImportLeads(String sUserID, String simSubscr){
        try {
            //create params on http using json object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sAgentIDx", sUserID);
            jsonObject.put("cSubscrbr", simSubscr);

            //send params and get result
            String loResponse = WebClient.sendRequest(loApi.getURLLeadCalls(),
                                                        jsonObject.toString(), poHeader.getHeaders());

            if(loResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }
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
            JSONArray loArray = loJson.getJSONArray("leads");

            //iterate rows from response
            for (int i = 0; i < loArray.length(); i++){
                JSONObject loResult = loArray.getJSONObject(i);

                String stransNox = loResult.getString("sTransNox");

                //initialize entity column values
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

                //get exisitng record on local database, if 0 then insert else update
                if (poDaoLeads.CountLeadTrans(stransNox) < 1){
                    poDaoLeads.SaveLeads(eLeadCalls);
                    Log.d(TAG, loResult.getString("sTransNox")+" has been saved to local");
                }else {
                    poDaoLeads.UpdateLeads(eLeadCalls);
                    Log.d(TAG, loResult.getString("sTransNox")+" has been updated to local");
                }

                //call method to IMPORT CLIENT INFO based on returned leads
                return ImportClients(loResult.getString("sClientID"));
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return false;
        }
    }
    public boolean ImportMCInquiries(){
        try {
            //send params and get result
            String loResponse = WebClient.sendRequest(loApi.getURLMCInq(),
                    new JSONObject().toString(), poHeader.getHeaders());

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
            emcInquiry.setdTransact(loInq.getString("dTransact"));
            emcInquiry.setsModelIDx(loInq.getString("sModelIDx"));
            emcInquiry.setsColorIDx(loInq.getString("sColorIDx"));
            emcInquiry.setsInquiryx(loInq.getString("sInquiryx"));
            emcInquiry.setdTargetxx(loInq.getString("dTargetxx"));
            emcInquiry.setdPurchase(loInq.getString("dPurchase"));
            emcInquiry.setdFollowUp(loInq.getString("dFollowUp"));
            emcInquiry.setsAgentIDx(loInq.getString("sAgentIDx"));
            emcInquiry.setcPurcType(loInq.getString("cPurcType"));
            emcInquiry.setsRemarks1(loInq.getString("sRemarks1"));
            emcInquiry.setsRemarks2(loInq.getString("sRemarks2"));
            emcInquiry.setsSourceNo(loInq.getString("sSourceNo"));
            emcInquiry.setsCreatedx(loInq.getString("sCreatedx"));
            emcInquiry.setdCreatedx(loInq.getString("dCreatedx"));
            emcInquiry.setcTranStat(loInq.getString("cTranStat"));
            emcInquiry.setsModified(loInq.getString("sModified"));
            emcInquiry.setdModified(loInq.getString("dModified"));

            if (poDaoMcInq.GetMCInquiry(loInq.getString("sTransNox")) == null){
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
}
