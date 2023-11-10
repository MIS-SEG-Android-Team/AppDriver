package org.rmj.g3appdriver.dev.Http.Headers;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.Config.DeviceConfig;
import org.rmj.g3appdriver.GCircle.Account.EmployeeSession;
import org.rmj.g3appdriver.dev.Http.HttpHeaderProvider;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GCircleHeaders implements HttpHeaderProvider {
    private static final String TAG = "LocalHeaders";

    private final Application instance;

    private static GCircleHeaders poInstance;

    private GCircleHeaders(Application instance){
        this.instance = instance;
    }

    public static GCircleHeaders getInstance(Application instance){
        if(poInstance == null){
            poInstance = new GCircleHeaders(instance);
        }

        return poInstance;
    }

    @Override
    public HashMap<String, String> getHeaders() {
        try {
            EmployeeSession loSession = EmployeeSession.getInstance(instance);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Calendar calendar = Calendar.getInstance();

            String lsUserIDx = loSession.getUserID();
            String lsClientx = loSession.getClientId();
            String lsLogNoxx = loSession.getLogNumber();
            String lsTokenxx = AppConfig.getInstance(instance).getFirebaseToken();
            String lsProduct = AppConfig.getInstance(instance).getProductID();
            String lsDevcIDx = DeviceConfig.getInstance(instance).getDeviceID();
            String lsDateTme = SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss");
            String lsDevcMdl = Build.MODEL;
            String lsMobileN = "09270359402";//DeviceConfig.getInstance(instance).getMobileNO();

            if (lsTokenxx.isEmpty()) {
                lsTokenxx = "f7qNSw8TRPWHSCga0g8YFF:APA91bG3i_lBPPWv9bbRasNzRH1XX1y0vzp6Ct8S_a-yMPDvSmud8FEVPMr26zZtBPHq2CmaIw9Rx0MZmf3sbuK44q3vQemUBoPPS4Meybw8pnTpcs3p0VbiTuoLHJtdncC6BgirJxt3";
            }

            Map<String, String> headers = new HashMap<>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json");
            headers.put("g-api-id", lsProduct);
            headers.put("g-api-client", lsClientx);
            headers.put("g-api-imei", lsDevcIDx);
            headers.put("g-api-model", lsDevcMdl);
            headers.put("g-api-mobile", lsMobileN);
            headers.put("g-api-token", lsTokenxx);
            headers.put("g-api-user", lsUserIDx);
            headers.put("g-api-key", lsDateTme);
            String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
            hash_toLower = hash_toLower.toLowerCase();
            headers.put("g-api-hash", hash_toLower);
            headers.put("g-api-log", lsLogNoxx);
            return (HashMap<String, String>) headers;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
