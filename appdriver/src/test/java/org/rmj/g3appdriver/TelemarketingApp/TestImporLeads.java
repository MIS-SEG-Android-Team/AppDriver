package org.rmj.g3appdriver.TelemarketingApp;

import static org.junit.Assert.assertTrue;

import android.app.Application;

import org.json.simple.JSONObject;
import org.junit.Test;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TestImporLeads {
    private static final String TAG = TestImporLeads.class.getSimpleName();
    private Boolean isSuccess;
    @SuppressWarnings("unchecked")
    @Test
    public void testImportLeads() throws Exception{
        String sURL = "http://192.168.10.68:8080/telemarketing_app/GetLeads.php";
        Calendar calendar = Calendar.getInstance();
        //Create the header section needed by the API
        Map<String, String> headers =
                new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", "gRider");
        headers.put("g-api-client", "GGC_BM001");
        headers.put("g-api-log", "GAP023113874");
        headers.put("g-api-imei", "MIS_SEG23");
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
        headers.put("g-api-hash", org.apache.commons.codec.digest.DigestUtils.md5Hex((String)headers.get("g-api-imei") + (String)headers.get("g-api-key")));
        headers.put("g-api-user", "GAP021002961");
        headers.put("g-api-mobile", "09171870011");
        headers.put("g-api-token", "ezEUjzzaR52VXJ7vwj01Bo:APA91bFFXB6ntjWlYDIlzlkG5qGwH2r0bLvfVwD7u0AlCKD64uw845-bVZ8yXMijxe0FUbNwYXgWX82VQxetxBNEHTpQp-H4V48hMZZbgmBCO7jylEG0RwkP8KldpVkJJu1FwLg4Yi5I");

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        param.put("sAgentIDx", "M001160024");
        param.put("cSubscrbr", "(cSubscrbr IN ('0', '1'))");

        String response = WebClient.sendRequest(sURL, param.toJSONString(), (HashMap<String, String>) headers);
        if(response == null){
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));
        }

        isSuccess = true;
        System.out.println(response);
        assertTrue(isSuccess);
    }
}
