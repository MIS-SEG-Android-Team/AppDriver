package org.rmj.g3appdriver.GConnect.Authentication.obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.lib.account.factory.iAuthenticate;
import org.rmj.g3appdriver.lib.account.pojo.PasswordUpdate;

public class UpdatePassword implements iAuthenticate {
    private static final String TAG = UpdatePassword.class.getSimpleName();

    private final Application instance;

    private String message;

    public UpdatePassword(Application instance) {
        this.instance = instance;
    }

    @Override
    public int DoAction(Object args) {
        try{
            PasswordUpdate loInfo = (PasswordUpdate) args;
            if(!loInfo.isDataValid()){
                message = loInfo.getMessage();
                return 0;
            }

            JSONObject params = new JSONObject();
            params.put("oldpswd", loInfo.getOldPassword());
            params.put("newpswd", loInfo.getNewPassword());

            String lsResponse = WebClient.sendRequest(
                    new GConnectApi(instance).getChangePasswordAPI(),
                    params.toString(),
                    HttpHeaderManager.getInstance(instance).initializeHeader().getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return 0;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                Log.e(TAG, message);
                return 0;
            }

            message = "Password updated successfully.";
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return 0;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
