package org.rmj.g3appdriver.GCircle.Apps.AndroidObject;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeRole;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeRole;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.WebClient;

import java.util.Date;
import java.util.List;

public class AuthorizeAccess {
    private static final String TAG = "A" +
            "" +
            "uthorizeAccess";

    private final Application instance;

    private final DEmployeeRole poDao;

    private String message;

    public AuthorizeAccess(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GCircleDB.getInstance(instance).employeeRoleDao();
    }

    public boolean importAccess(){
        try{
            JSONObject params = new JSONObject();
            String lsResponse = WebClient.sendRequest(
                    new GCircleApi(instance).getRequestUserAccess(),
                    params.toString(),
                    HttpHeaderManager.getInstance(instance).initializeHeader().getHeaders());

            if (lsResponse == null) {
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

            JSONArray loArr = loResponse.getJSONArray("payload");
            for(int x = 0; x < loArr.length(); x++){
                JSONObject loJson = loArr.getJSONObject(x);
                EEmployeeRole loRole = poDao.GetEmployeeRole(loJson.getString("sObjectNm"));
                if(loRole == null){
                    EEmployeeRole loDetail = new EEmployeeRole();
                    loDetail.setObjectNm(loJson.getString("sObjectNm"));
                    loDetail.setObjectDs(loJson.getString("sObjectDs"));
                    loDetail.setObjectTP(loJson.getString("cObjectTP"));
                    loDetail.setParentxx(loJson.getString("sParentxx"));
                    loDetail.setHasChild(loJson.getString("cHasChild"));
                    loDetail.setSeqnceCd(loJson.getString("sSeqnceCD"));
                    loDetail.setRecdStat(loJson.getString("cRecdStat"));
                    loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                    poDao.InsertEmployeeRole(loDetail);
                    Log.d(TAG, "Authorize feature has been save.");
                } else {
                    Date ldDate1 = SQLUtil.toDate(loRole.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                    if (!ldDate1.equals(ldDate2)){
                        //create update statement

                        loRole.setObjectNm(loJson.getString("sObjectNm"));
                        loRole.setObjectTP(loJson.getString("cObjectTP"));
                        loRole.setObjectDs(loJson.getString("sObjectDs"));
                        loRole.setParentxx(loJson.getString("sParentxx"));
                        loRole.setHasChild(loJson.getString("cHasChild"));
                        loRole.setSeqnceCd(loJson.getString("sSeqnceCD"));
                        loRole.setRecdStat(loJson.getString("cRecdStat"));
                        loRole.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.UpdateRole(loRole);
                        Log.d(TAG, "Authorize feature has been updated.");
                    }
                }
            }

            message = "Authorize features for user has been imported successfully.";
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public LiveData<List<EEmployeeRole>> getAuthorizeAccess(){
        return poDao.getEmployeeRoles();
    }

    public LiveData<List<EEmployeeRole>> getChildRoles(){
        return poDao.getChildRoles();
    }
}
