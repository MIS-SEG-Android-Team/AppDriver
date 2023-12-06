package org.rmj.g3appdriver.TelemarketingApp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.utils.SQLUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class TestImportClientMobile {
    private Map<String, String> headers;
    @Before
    public void SetUp(){
        /*NOTE: RUN THIS ON 192.168.10.224 (TEST DATABASE) TO INITIALIZE HEADERS PROPERLY
         * RUN: SELECT * FROM xxxSysUserLog WHERE  sUserIDxx = 'GAP0190004' AND sLogNoxxx = "GAP023110901" AND sProdctID = "gRider";
         * REQUIRED: Change 'dLogInxxx' column date to current date.*/

        Calendar calendar = Calendar.getInstance();
        //Create the header section needed by the API
        headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", "gRider");
        headers.put("g-api-client", "GGC_BM001");
        headers.put("g-api-log", "GAP023110901");
        headers.put("g-api-imei", "GMC_SEG09");
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
        headers.put("g-api-hash", org.apache.commons.codec.digest.DigestUtils.md5Hex((String)headers.get("g-api-imei") + (String)headers.get("g-api-key")));
        headers.put("g-api-user", "GAP0190004");
        headers.put("g-api-mobile", "09260375777");
        headers.put("g-api-token", "12312312");

    }
    @Test
    public void TestImportClientMobile() throws Exception{
        String sURL = "http://192.168.10.68:8080/telemarketing_app/GetClientMobile.php";

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        param.put("sClientID","M02020000513");
        param.put("sMobileNo","09304422091");

        String response = WebClient.sendRequest(sURL, param.toString(), (HashMap<String, String>) headers);
        if(response == null){
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));
        }

        JSONObject loJson = new JSONObject(response);
        assertNotNull(loJson);

        JSONObject loCMob = loJson.getJSONObject("clientmobile");
        assertNotNull(loCMob);

        System.out.println(loCMob.get("sClientID"));
        System.out.println(loCMob.get("nEntryNox"));
        System.out.println(loCMob.get("sMobileNo"));
        System.out.println(loCMob.get("nPriority"));
        System.out.println(loCMob.get("cIncdMktg"));
        System.out.println(loCMob.get("nUnreachx"));
        System.out.println(loCMob.get("dLastVeri"));
        System.out.println(loCMob.get("dInactive"));
        System.out.println(loCMob.get("nNoRetryx"));
        System.out.println(loCMob.get("cInvalidx"));
        System.out.println(loCMob.get("sIdleTime"));
        System.out.println(loCMob.get("cConfirmd"));
        System.out.println(loCMob.get("dConfirmd"));
        System.out.println(loCMob.get("cSubscrbr"));
        System.out.println(loCMob.get("dHoldMktg"));
        System.out.println(loCMob.get("dLastCall"));
        System.out.println(loCMob.get("cRecdStat"));
    }
}
