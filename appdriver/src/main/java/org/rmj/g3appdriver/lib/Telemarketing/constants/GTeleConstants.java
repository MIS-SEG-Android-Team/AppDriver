package org.rmj.g3appdriver.lib.Telemarketing.constants;

public class GTeleConstants {
    public String getLeadsrc(String value){
        switch (value){
            case "GANADO":
                return "GNDO";
            case "MC CREDIT APPLICATION":
                return "MCCA";
            case "MC INQUIRY ":
                return "INQR";
            case "LENDING":
                return "LEND";
            case "MC SALES":
                return "MCSO";
            case "MOBILE PHONE INQUIRY":
                return "MPIn";
        }
        return "DEFAULT";
    }
    public int getSimSubscriber(String value){
        switch (value){
            case "GLOBE":
                return 0;
            case "SMART":
                return 1;
            case "SUN":
                return 2;
        }
        return 3;
    }
}
