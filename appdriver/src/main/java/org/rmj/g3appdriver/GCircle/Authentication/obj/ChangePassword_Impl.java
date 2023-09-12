package org.rmj.g3appdriver.GCircle.Authentication.obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.HttpHeaderProvider;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.lib.authentication.factory.iAuthenticate;
import org.rmj.g3appdriver.lib.authentication.pojo.PasswordCredentials;

public class ChangePassword_Impl implements iAuthenticate {
    private static final String TAG = ChangePassword_Impl.class.getSimpleName();

    private final Application instance;

    private String message;

    public ChangePassword_Impl(Application instance) {
        this.instance = instance;
    }

    @Override
    public int DoAction(Object args) {
        try{
            PasswordCredentials loInfo = (PasswordCredentials) args;
            PasswordCredentials.PasswordValidator loValidator = new PasswordCredentials.PasswordValidator();
            if(!loValidator.isDataValid(loInfo)){
                message = loValidator.getMessage();
                return 0;
            }

            JSONObject params = new JSONObject();
            params.put("oldpswd", loInfo.getOldPassword());
            params.put("newpswd", loInfo.getNewPassword());

            HttpHeaderProvider loHeaders = HttpHeaderManager.getInstance(instance).initializeHeader();

            String lsResponse = WebClient.sendRequest(
                    new GCircleApi(instance).getUrlChangePassword(),
                    params.toString(),
                    loHeaders.getHeaders());
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
