package org.rmj.g3appdriver.GCircle.Account;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.lib.accountmanagement.factory.AccountManagement;
import org.rmj.g3appdriver.lib.authentication.pojo.PasswordCredentials;

public class GCircleAccManagementImpl implements AccountManagement {
    private static final String TAG = "GCircleAccManagementImp";

    private final Application instance;

    private String message;

    public GCircleAccManagementImpl(Application instance) {
        this.instance = instance;
    }

    @Override
    public boolean changePassword(PasswordCredentials foPassword) {
        try{
            PasswordCredentials.PasswordValidator loValidator = new PasswordCredentials.PasswordValidator();

            if(!loValidator.isDataValid(foPassword)){
                message = loValidator.getMessage();
                return false;
            }

            JSONObject params = new JSONObject();
            params.put("oldpswd", foPassword.getOldPassword());
            params.put("newpswd", foPassword.getNewPassword());

            String lsResponse = WebClient.sendRequest(
                    new GConnectApi(instance).getChangePasswordAPI(),
                    params.toString(),
                    HttpHeaderManager.getInstance(instance).initializeHeader().getHeaders());

            if(lsResponse == null){
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

            message = "Password updated successfully.";

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    @Override
    public boolean deactivateAccount() {
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);


            return false;
        }
    }

    @Override
    public boolean reactivateAccount(String fsEmail) {
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
