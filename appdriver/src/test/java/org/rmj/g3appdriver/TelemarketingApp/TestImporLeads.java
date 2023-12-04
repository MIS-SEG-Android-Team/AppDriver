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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RunWith(JUnit4.class)
public class TestImporLeads {
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
    public void TestImportLeads() throws Exception{
        String sURL = "http://192.168.10.68:8080/telemarketing_app/GetLeads.php";

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        param.put("sAgentIDx", "M001160024");
        param.put("cSubscrbr", "(cSubscrbr IN ('0', '1'))");

        String response = WebClient.sendRequest(sURL, param.toString(), (HashMap<String, String>) headers);
        if(response == null){
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));
        }

        System.out.println(response);

        JSONObject jsonResponse = new JSONObject(response);
        assertNotNull(jsonResponse);

        JSONArray loArray = jsonResponse.getJSONArray("initleads");
        assertTrue(loArray.length() > 0);

        for (int i = 0; i < loArray.length(); i++){
            JSONObject loLeads = loArray.getJSONObject(i).getJSONObject("leads");

            System.out.println(loLeads.get("sTransNox"));
            System.out.println(loLeads.get("sAgentIDx"));
            System.out.println(loLeads.get("dTransact"));
            System.out.println(loLeads.get("sClientID"));
            System.out.println(loLeads.get("sMobileNo"));
            System.out.println(loLeads.get("sRemarksx"));
            System.out.println(loLeads.get("sReferNox"));
            System.out.println(loLeads.get("sSourceCD"));
            System.out.println(loLeads.get("sApprovCd"));
            System.out.println(loLeads.get("cTranStat"));
            System.out.println(loLeads.get("dCallStrt"));
            System.out.println(loLeads.get("dCallEndx"));
            System.out.println(loLeads.get("nNoRetryx"));
            System.out.println(loLeads.get("cSubscrbr"));
            System.out.println(loLeads.get("cCallStat"));
            System.out.println(loLeads.get("cTLMStatx"));
            System.out.println(loLeads.get("cSMSStatx"));
            System.out.println(loLeads.get("nSMSSentx"));
            System.out.println(loLeads.get("sModified"));
            System.out.println(loLeads.get("dModified"));
        }
    }
}
