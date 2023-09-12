package org.rmj.g3appdriver.GConnect.GCard.Service;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DServiceInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EServiceInfo;
import org.rmj.g3appdriver.GConnect.room.GGC_GConnectDB;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.dev.encryp.CodeGenerator;

public class MCService {
    private static final String TAG = MCService.class.getSimpleName();

    private final Application instance;

    private final DServiceInfo poDao;

    private String message;

    public MCService(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GConnectDB.getInstance(instance).EServiceDao();
    }

    public boolean ImportServiceInfo(){
        try{
            String lsGcardNo = poDao.GetGCardNumber();

            JSONObject params = new JSONObject();
            String lsSecureNo = CodeGenerator.generateSecureNo(lsGcardNo);
            params.put("secureno", lsSecureNo);

            String lsResponse = WebClient.sendRequest(
                    new GConnectApi(instance).getServiceInfoAPI(),
                    params.toString(),
                    HttpHeaderManager.getInstance(instance).initializeHeader().getHeaders());

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

            JSONArray laJson = loResponse.getJSONArray("detail");
            if(laJson.length() > 0) {
                for (int x = 0; x < laJson.length(); x++) {
                    JSONObject loJson = laJson.getJSONObject(x);
                    EServiceInfo loService = new EServiceInfo();
                    loService.setGCardNox(lsGcardNo);
                    loService.setSerialID(loJson.getString("sSerialID"));
                    loService.setEngineNo(loJson.getString("sEngineNo"));
                    loService.setFrameNox(loJson.getString("sFrameNox"));
                    loService.setModelNme(loJson.getString("sModelNme"));
                    loService.setFSEPStat(loJson.getString("cFSEPStat"));
                    loService.setLastSrvc(loJson.getString("dLastSrvc"));
                    loService.setPurchase(loJson.getString("dPurchase"));
                    loService.setYellowxx(loJson.getInt("nYellowxx"));
                    loService.setWhitexxx(loJson.getInt("nWhitexxx"));
                    loService.setMIlAgexx(loJson.getInt("nMilagexx"));
                    loService.setNxtRmnds(loJson.getString("dNxtRmndS"));
                    poDao.insert(loService);
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean ScheduleMcService(){
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
