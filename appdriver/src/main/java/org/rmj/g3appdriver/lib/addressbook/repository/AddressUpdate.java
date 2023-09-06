package org.rmj.g3appdriver.lib.addressbook.repository;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.GConnect.room.Entities.EClientInfo;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConstants;

public class AddressUpdate {
    private static final String TAG = "AddressUpdate";

    private final Application instance;

    private String message;

    public AddressUpdate(Application instance) {
        this.instance = instance;
    }

    public String getMessage() {
        return message;
    }

    public boolean UpdateBillingAddress(EClientInfo foVal){
        try{
            JSONObject param = new JSONObject();
            param.put("dTransact", AppConstants.CURRENT_DATE());
            param.put("sHouseNo1", foVal.getHouseNo1());
            param.put("sAddress1", foVal.getAddress1());
            param.put("sBrgyIDx1", foVal.getBrgyIDx1());
            param.put("sTownIDx1", foVal.getTownIDx1());
            param.put("sHouseNo2", foVal.getHouseNo2());
            param.put("sAddress2", foVal.getAddress2());
            param.put("sBrgyIDx2", foVal.getBrgyIDx2());
            param.put("sTownIDx2", foVal.getTownIDx2());

            String lsResponse = WebClient.sendRequest(
                    new GConnectApi(instance).getAddressUpdateAPI(),
                    param.toString(),
                    HttpHeaderManager.getInstance(instance).initializeHeader().getHeaders());

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



        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
