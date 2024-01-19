package org.rmj.g3appdriver.GCircle.Authentication.obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.Config.DeviceConfig;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.HttpHeaderProvider;
import org.rmj.g3appdriver.lib.authentication.pojo.LoginCredentials;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeRole;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Device.Telephony;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.authentication.factory.iAuthenticate;
import org.rmj.g3appdriver.GCircle.Account.EmployeeSession;
import org.rmj.g3appdriver.lib.Version.AppVersion;

public class EmployeeAuth_Impl implements iAuthenticate {
    private static final String TAG = EmployeeAuth_Impl.class.getSimpleName();

    private final DEmployeeInfo poDao;
    private final DEmployeeRole roleDao;
    private final EmployeeSession poSession;
    private final GCircleApi poApi;
    private final HttpHeaderProvider poHeaders;
    private final DeviceConfig poConfig;
    private final Telephony poDevID;
    private final AppVersion poVersion;

    private String message;

    public EmployeeAuth_Impl(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).EmployeeDao();
        this.roleDao = GGC_GCircleDB.getInstance(instance).employeeRoleDao();
        this.poSession = EmployeeSession.getInstance(instance);
        this.poConfig = DeviceConfig.getInstance(instance);
        this.poApi = new GCircleApi(instance);
        this.poVersion = new AppVersion(instance);
        this.poHeaders = HttpHeaderManager.getInstance(instance).initializeHeader();
        this.poDevID = new Telephony(instance);
    }

    @Override
    public int DoAction(Object args) {
        try{
            LoginCredentials loInfo = (LoginCredentials) args;
            LoginCredentials.EntryValidator loValidator = new LoginCredentials.EntryValidator();
            if(!loValidator.isDataValid(loInfo)){
                message = loValidator.getMessage();
                return 0;
            }

            //GET DEVICE MOBILE NO FROM SIMCARD SUBSCRIPTION
            if(poConfig.getMobileNO().isEmpty()){
                poConfig.setMobileNO(poDevID.getMobilNumbers());
                Log.d(TAG, "Mobile number has been initialized.");
            }

            JSONObject params = new JSONObject();
            params.put("user", loInfo.getEmail());
            params.put("pswd", loInfo.getPassword());

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlAuthEmployee(),
                    params.toString(),
                    poHeaders.getHeaders());
            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return 0;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return 0;
            }

            EEmployeeInfo employeeInfo = new EEmployeeInfo();
            employeeInfo.setClientID(loResponse.getString("sClientID"));
            employeeInfo.setBranchCD(loResponse.getString("sBranchCD"));
            employeeInfo.setBranchNm(loResponse.getString("sBranchNm"));
            employeeInfo.setLogNoxxx(loResponse.getString("sLogNoxxx"));
            employeeInfo.setUserIDxx(loResponse.getString("sUserIDxx"));
            employeeInfo.setEmailAdd(loResponse.getString("sEmailAdd"));
            employeeInfo.setUserName(loResponse.getString("sUserName"));
            employeeInfo.setUserLevl(loResponse.getString("nUserLevl"));
            employeeInfo.setSlfieLog(loResponse.getString("cSlfieLog"));
            employeeInfo.setDeptIDxx(loResponse.getString("sDeptIDxx"));
            employeeInfo.setPositnID(loResponse.getString("sPositnID"));
            employeeInfo.setEmpLevID(loResponse.getInt("sEmpLevID"));
            employeeInfo.setAllowUpd(loResponse.getString("cAllowUpd"));
            employeeInfo.setEmployID(loResponse.getString("sEmployID"));
            employeeInfo.setDeviceID(poDevID.getDeviceID());
            employeeInfo.setModelIDx(Build.MODEL);
            employeeInfo.setMobileNo(poConfig.getMobileNO());
            employeeInfo.setLoginxxx(AppConstants.DATE_MODIFIED());
            employeeInfo.setSessionx(AppConstants.CURRENT_DATE());
            poDao.RemoveSessions();
            poDao.SaveNewEmployeeSession(employeeInfo);

            String lsClientx = loResponse.getString("sClientID");
            String lsUserIDx = loResponse.getString("sUserIDxx");
            String lsUserNme = loResponse.getString("sUserName");
            String lsLogNoxx = loResponse.getString("sLogNoxxx");
            String lsBranchx = loResponse.getString("sBranchCD");
            String lsBranchN = loResponse.getString("sBranchNm");
            String lsDeptIDx = loResponse.getString("sDeptIDxx");
            String lsEmpIDxx = loResponse.getString("sEmployID");
            String lsPostIDx = loResponse.getString("sPositnID");
            String lsEmpLvlx = loResponse.getString("sEmpLevID");
            poSession.initUserSession(lsUserIDx, lsUserNme, lsClientx, lsLogNoxx, lsBranchx, lsBranchN, lsDeptIDx, lsEmpIDxx, lsPostIDx, lsEmpLvlx, "1");
            message = "Login success";

            Thread.sleep(1000);
            if (poVersion.SubmitUserAppVersion()){
                Log.d(TAG, "User app version uploaded.");
            } else {
                Log.d(TAG, poVersion.getMessage());
            }

            Thread.sleep(1000);

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
