package org.rmj.g3appdriver.lib.Telemarketing.constants;

public class GTeleConstants {
    public int GetSimSubscriber(String value){
        switch (value){
            case "GLOBE":
                return 0;
            case "SMART":
                return 1;
            case "SUN":
                return 2;
            case "DITO":
                return 3;
            case "T-Mobile": //for testing on emulator, default simcard
                return 0;
            default:
                return 4; //no return value
        }
    }
    public String GetRemarks(String sRemarks){
        switch (sRemarks){
            case "NOT INTERESTED":
                return "NI";
            case "NOT NOW":
                return "NN";
            case "POSSIBLE SALES":
                return "PS";
            case "NO CAPACITY":
                return "NC";
            case "CALL BACK":
                return "CB";
            case "ANSWERING MACHINE":
                return "AM";
            case "WRONG NUMBER":
                return "WN";
            case "NO ANSWER":
                return "NA";
            case "CANNOT BE REACHED":
                return "UR";
        }
        return "";
    }
}
