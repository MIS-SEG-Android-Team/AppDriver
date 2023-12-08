package org.rmj.g3appdriver.lib.Telemarketing.constants;

public class GTeleConstants {
    public String GetLeadConstant(String value){
        switch (value){
            case "GANADO":
                return "GNDO";
            case "MC CREDIT APPLICATION":
                return "MCCA";
            case "MC INQUIRY":
                return "INQR";
            case "LENDING":
                return "LEND";
            case "MC SALES":
                return "MCSO";
            case "MOBILE PHONE INQUIRY":
                return "MPIn";
            case "RANDOM CALL":
                return "TLMC";
            case "REFERRAL":
                return "RFRL";
            case "OTHERS":
                return "OTH";
            case "BIYAHENG FIESTA":
                return "GBF";
            case "FREE SERVICE":
                return "FSCU";
            case "DISPLAY CARAVAN":
                return "DC";
            case "INCOMING CALL":
                return "CALL";
            default:
                return "";
        }
    }
    public int GetSimSubscriber(String value){
        switch (value){
            case "GLOBE":
                return 0;
            case "SMART":
                return 1;
            case "SUN":
                return 2;
            case "T-Mobile": //for testing on emulator, default simcard
                return 0;
            default:
                return 3; //no return value
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
