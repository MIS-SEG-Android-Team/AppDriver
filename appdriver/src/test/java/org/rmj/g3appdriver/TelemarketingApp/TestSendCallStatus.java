package org.rmj.g3appdriver.TelemarketingApp;

import static org.junit.Assert.assertNotNull;
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
public class TestSendCallStatus {
    private Map<String, String> headers;
    @Before
    public void SetUp(){
        /*NOTE: RUN THIS ON 192.168.10.224 (TEST DATABASE) TO INITIALIZE HEADERS PROPERLY
         * RUN: SELECT * FROM
         *
         *  xxxSysUserLog WHERE  sUserIDxx = 'GAP0190004' AND sLogNoxxx = "GAP023110901" AND sProdctID = "gRider";
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
    public void TestSendCallUpdate() throws Exception {
        //change this value to identify, which specific table will be affected.
        //PS & NN, Hotline_Outgoing. (INSERT AS NEW ROW)
        //UR, Client_Mobile. (UPDATE nUnreachx, dLastCall)
        //ALL STATUS, Call_Outgoin. (UPDATE cTLMStatx)
        String sCallStat = "PS";

        String sURL = "http://192.168.10.68:8080/telemarketing_app/SaveCallStat.php";

        JSONObject jsonParam = new JSONObject();

        //PARAM FOR CALL_OUTGOING UPDATE
        jsonParam.put("sCallStat", sCallStat);
        jsonParam.put("sDivision", "TLM");
        jsonParam.put("cSendStat", "0");
        jsonParam.put("nNoRetryx", 0);
        jsonParam.put("sUDHeader", "");
        jsonParam.put("cTranStat", "2");
        jsonParam.put("nPriority", "1");
        jsonParam.put("sReferNox", "M0T123072843");
        jsonParam.put("dCallStrt", "2023-12-08 11:40:33");
        jsonParam.put("dCallEndx", "2023-12-08 11:50:33");

        //PARAM FOR HOTLINE OUTGOING INSERT
        jsonParam.put("cSubscr", "1");
        jsonParam.put("sApprvCd", "MX0111112301");
        jsonParam.put("sUserID", "M001160024");

        //PARAM FOR CLIENT MOBILE UPDATE
        jsonParam.put("sClientID", "M16323000812");
        jsonParam.put("sMobileNo", "09188118093");

        String lsResponse = WebClient.sendRequest( sURL, jsonParam.toString(), (HashMap<String, String>) headers);
        if(lsResponse == null){
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));
        }

        System.out.println(lsResponse);

        JSONObject loResponse = new JSONObject(lsResponse);
        assertNotNull(loResponse);

        JSONObject loTransData = loResponse.getJSONObject("transparams");
        assertNotNull(loTransData);

        System.out.println(loTransData.get("dTransact"));

        if (sCallStat == "PS" || sCallStat == "NN"){
            System.out.println(loTransData.get("sTransNox"));
            System.out.println(loTransData.get("sDivision"));
            System.out.println(loTransData.get("cSendStat"));
            System.out.println(loTransData.get("nNoRetryx"));
            System.out.println(loTransData.get("sUDHeader"));
            System.out.println(loTransData.get("cTranStat"));
            System.out.println(loTransData.get("nPriority"));
            System.out.println(loTransData.get("sMessagex"));
            System.out.println(loTransData.get("sSourceCd"));
            System.out.println(loTransData.get("dDueDate"));
        }
    }
}
