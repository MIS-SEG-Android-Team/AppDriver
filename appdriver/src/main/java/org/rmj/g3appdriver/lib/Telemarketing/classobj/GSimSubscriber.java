package org.rmj.g3appdriver.lib.Telemarketing.classobj;

import static android.content.Context.TELEPHONY_SERVICE;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

public class GSimSubscriber {
    private Context context;
    private TelephonyManager telephonyManager;
    private String simSlot1;
    private String simSlot2;
    private String message;
    public GSimSubscriber(Context context){
        this.context = context;
        this.telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
    }
    public String getMessage(){
        return message;
    }
    public boolean InitSim(){
        //check device version for dual sim os supported
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int slot1State = telephonyManager.getSimState(0);
            int slot2State = telephonyManager.getSimState(1);

            switch (slot1State){
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    message= "UNKNOWN STATE FOR SLOT 1";
                    return false;
                case TelephonyManager.SIM_STATE_ABSENT:
                    message= "NO SIM CARD DETECTED FOR SLOT 1";
                    return false;
                case TelephonyManager.SIM_STATE_NOT_READY:
                    message= "SIM CARD NOT READY SLOT 1";
                    return false;
                case TelephonyManager.SIM_STATE_READY:
                    setSimSlot1(telephonyManager.getSimOperatorName());
                    return true;
            }

            switch (slot2State){
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    message= "UNKNOWN STATE FOR SLOT 2";
                    return false;
                case TelephonyManager.SIM_STATE_ABSENT:
                    message= "NO SIM CARD DETECTED FOR SLOT 2";
                    return false;
                case TelephonyManager.SIM_STATE_NOT_READY:
                    message= "SIM CARD NOT READY SLOT 2";
                    return false;
                case TelephonyManager.SIM_STATE_READY:
                    setSimSlot2(telephonyManager.getSimOperatorName());
                    return true;
            }
            return false;
        }else {
            int slotState = telephonyManager.getSimState();
            switch (slotState) {
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    message = "UNKNOWN STATE";
                    return false;
                case TelephonyManager.SIM_STATE_ABSENT:
                    message = "NO SIM CARD DETECTED";
                    return false;
                case TelephonyManager.SIM_STATE_NOT_READY:
                    message = "SIM CARD NOT READY";
                    return false;
                case TelephonyManager.SIM_STATE_READY:
                    setSimSlot1(telephonyManager.getSimOperatorName());
                    return true;
            }
        }
        return false;
    }
    public void setSimSlot1(String simSlot1) {
        this.simSlot1 = simSlot1;
    }
    public void setSimSlot2(String simSlot2) {
        this.simSlot2 = simSlot2;
    }
    public String getSimSlot2() {
        return simSlot2;
    }
    public String getSimSlot1() {
        return simSlot1;
    }
}
