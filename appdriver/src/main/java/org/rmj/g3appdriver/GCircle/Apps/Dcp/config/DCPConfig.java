package org.rmj.g3appdriver.GCircle.Apps.Dcp.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class DCPConfig {
    private static final String TAG = "DCPConfig";

    private final SharedPreferences poPref;
    private final SharedPreferences.Editor poEdit;

    private static final String CONFIG_NAME = "GGC_AppConfig";
    private final int PRIV_MODE = 0;

    private static final String DCP_PR_NUMBER = "nPRNumber";
    private static final String DCP_REBATE = "nPayRebte";

    private static DCPConfig poInstance;

    private DCPConfig(Context context){
        poPref = context.getSharedPreferences(CONFIG_NAME, PRIV_MODE);
        poEdit = poPref.edit();
    }

    public static DCPConfig getInstance(Context context){
        if(poInstance == null){
            poInstance = new DCPConfig(context);
        }

        return poInstance;
    }

    public void setDcpPrNumber(int fnPrNumber){
        poEdit.putInt(DCP_PR_NUMBER, fnPrNumber);
        poEdit.commit();
    }

    public String getDcpPrNumber(){
        int lnPrNox = poPref.getInt(DCP_PR_NUMBER, 0) + 1;
        return String.format("%08d", lnPrNox);
    }

    public void setPaymentRebate(float fnRebate){
        poEdit.putFloat(DCP_REBATE, fnRebate);
        poEdit.commit();
    }

    public float getPaymentRebate(){
        return poPref.getFloat(DCP_REBATE, 0);
    }
}
