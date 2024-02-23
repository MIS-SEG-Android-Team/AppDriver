package org.rmj.g3appdriver.GCircle.Apps.TeleMarketing;

public class LeadsInformation {
    public String sTransNox;
    public String sReferNox;
    public String sSourceCD;
    public String sClientID;
    public String sMobileNo;
    public String cTranStat;
    public String sSubscr;
    public String sUserID;
    private String message;
    public String getMessage(){
        return message;
    }

    public String getsTransNox() {
        return sTransNox;
    }

    public void setsTransNox(String sTransNox) {
        this.sTransNox = sTransNox;
    }

    public String getsReferNox() {
        return sReferNox;
    }

    public void setsReferNox(String sReferNox) {
        this.sReferNox = sReferNox;
    }

    public String getsSourceCD() {
        return sSourceCD;
    }

    public void setsSourceCD(String sSourceCD) {
        this.sSourceCD = sSourceCD;
    }

    public String getsClientID() {
        return sClientID;
    }

    public void setsClientID(String sClientID) {
        this.sClientID = sClientID;
    }

    public String getsMobileNo() {
        return sMobileNo;
    }

    public void setsMobileNo(String sMobileNo) {
        this.sMobileNo = sMobileNo;
    }

    public String getcTranStat() {
        return cTranStat;
    }

    public void setcTranStat(String cTranStat) {
        this.cTranStat = cTranStat;
    }

    public String getsSubscr() {
        return sSubscr;
    }

    public void setsSubscr(String sSubscr) {
        this.sSubscr = sSubscr;
    }

    public String getsUserID() {
        return sUserID;
    }

    public void setsUserID(String sUserID) {
        this.sUserID = sUserID;
    }
    public Boolean isDataValid(){
        if (sTransNox.isEmpty()){
            message = "Transaction number is empty";
            return false;
        }
        if (sReferNox.isEmpty()){
            message = "Source number is empty";
            return false;
        }
        if (sSourceCD.isEmpty()){
            message = "Source type is empty";
            return false;
        }
        if (sClientID.isEmpty()){
            message = "Client ID is empty";
            return false;
        }
        if (sMobileNo.isEmpty()){
            message = "Mobile number is empty";
            return false;
        }
        if (cTranStat.isEmpty()){
            message = "Transaction status is empty";
            return false;
        }
        if (sSubscr.isEmpty()){
            message = "Mobile subscriber is empty";
            return false;
        }
        return true;
    }
}
