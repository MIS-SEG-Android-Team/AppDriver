package org.rmj.g3appdriver.TelemarketingApp;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJsonParams {
    @Test
    public void JSONParamsSameVar() {
        try {
            String sRemarks = null;

            JSONObject params = new JSONObject();
            params.put("sRemarks", sRemarks == null ? "": sRemarks);

            System.out.println(params);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void JSONParamsDifferentVar() {
        try {
            String sApprovCode = "SX19GK";
            String sRemarks = null;

            JSONObject params = new JSONObject();
            params.put("sRemarks", sApprovCode == null ? "": sRemarks);

            System.out.println(params);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
