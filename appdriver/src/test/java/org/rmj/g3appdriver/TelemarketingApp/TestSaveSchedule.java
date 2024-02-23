package org.rmj.g3appdriver.TelemarketingApp;

import static org.junit.Assert.assertNotNull;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.rmj.g3appdriver.dev.Http.WebClient;
import org.rmj.g3appdriver.lib.Telemarketing.constants.GTeleConstants;
import org.rmj.g3appdriver.utils.SQLUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RunWith(JUnit4.class)
public class TestSaveSchedule {
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
    public void TestSaveSchedule() throws Exception {

        String sURL = "http://192.168.10.68:8080/telemarketing_app/SaveSchedule.php";

        JSONObject jsonParam = new JSONObject();

        jsonParam.put("sTransNox", "M0T123000816");
        jsonParam.put("sLeadSrc", "INQR");
        jsonParam.put("dFollowUp", "2023-11-21");
        jsonParam.put("cTransStat", "0");
        jsonParam.put("sRemarks", "Reschedule mo boy testing");
        jsonParam.put("sUserID", "M001160024");

        String lsResponse = WebClient.sendRequest( sURL, jsonParam.toString(), (HashMap<String, String>) headers);
        if(lsResponse == null){
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));
        }

        System.out.println(lsResponse);

        JSONObject loResponse = new JSONObject(lsResponse);
        assertNotNull(loResponse);

        JSONObject loSchedule = loResponse.getJSONObject("schedule");
        assertNotNull(loSchedule);

        System.out.println(loSchedule.get("sTransNox"));
        System.out.println(loSchedule.get("dFollowUp"));
        System.out.println(loSchedule.get("tablenm"));
    }
}
