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
    public String GetRemarksConstants(String sConstants){
        switch (sConstants){
            case "NI":
                return "NOT INTERESTED";
            case "NN":
                return "NOT NOW";
            case "PS":
                return "POSSIBLE SALES";
            case "NC":
                return "NO CAPACITY";
            case "CB":
                return "CALL BACK";
            case "AM":
                return "ANSWERING MACHINE";
            case "WN":
                return "WRONG NUMBER";
            case "NA":
                return "NO ANSWER";
            case "UR":
                return "CANNOT BE REACHED";
        }
        return "";
    }
}
