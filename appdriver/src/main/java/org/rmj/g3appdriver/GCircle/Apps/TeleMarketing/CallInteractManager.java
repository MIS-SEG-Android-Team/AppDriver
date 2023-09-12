package org.rmj.g3appdriver.GCircle.Apps.TeleMarketing;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.Apps.TeleMarketing.pojo.CallClient;
import org.rmj.g3appdriver.GCircle.Apps.TeleMarketing.pojo.CallResult;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.HttpHeaderProvider;
import org.rmj.g3appdriver.dev.Http.WebClient;

public class CallInteractManager {
    private static final String TAG = "CallInteractManager";

    private final Application instance;
    private final HttpHeaderProvider poHeader;
    private final GCircleApi poApi;

    private String message;

    public CallInteractManager(Application instance) {
        this.instance = instance;
        this.poHeader = HttpHeaderManager.getInstance(instance).initializeHeader();
        this.poApi = new GCircleApi(instance);
    }

    public CallClient getNextCall(){
        try{
            String lsResponse = WebClient.sendRequest(
                    "",
                    new JSONObject().toString(),
                    poHeader.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");

            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            CallClient loClient = new CallClient("", "", "");

            return loClient;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public boolean TagPreviuosCall(CallResult foResult){
        try{
            CallResult.CallResultValidator loValidator = new CallResult.CallResultValidator();
            if(loValidator.isDataValid(foResult)){
                message = loValidator.getMessage();
                return false;
            }

            String lsResponse = WebClient.sendRequest(
                    "",
                    new JSONObject().toString(),
                    poHeader.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");

            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
