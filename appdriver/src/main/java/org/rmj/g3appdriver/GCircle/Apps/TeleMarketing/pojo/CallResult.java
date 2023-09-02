package org.rmj.g3appdriver.GCircle.Apps.TeleMarketing.pojo;

public class CallResult {

    private String sTransNox = "";
    private String sRemarksx = "";
    private String sAddRmrks = "";
    private String nLenghtxx = "";
    private String sInquiryx = "";
    private String dDateSchd = "";

    public CallResult() {
    }

    public String getTransNox() {
        return sTransNox;
    }

    public void setTransNox(String sTransNox) {
        this.sTransNox = sTransNox;
    }

    public String getRemarksx() {
        return sRemarksx;
    }

    public void setRemarksx(String sRemarksx) {
        this.sRemarksx = sRemarksx;
    }

    public String getAddRmrks() {
        return sAddRmrks;
    }

    public void setAddRmrks(String sAddRmrks) {
        this.sAddRmrks = sAddRmrks;
    }

    public String getLenghtxx() {
        return nLenghtxx;
    }

    public void setLenghtxx(String nLenghtxx) {
        this.nLenghtxx = nLenghtxx;
    }

    public String getInquiryx() {
        return sInquiryx;
    }

    public void setInquiryx(String sInquiryx) {
        this.sInquiryx = sInquiryx;
    }

    public static class CallResultValidator{

        private String message;

        public boolean isDataValid(CallResult foResult){
            if(foResult.getTransNox().isEmpty()){
                message = "Call transaction number is empty.";
                return false;
            }

            if(foResult.getRemarksx().isEmpty()){
                message = "Please select remarks.";
                return false;
            }

            if(foResult.getAddRmrks().isEmpty()){
                message = "Please enter additional remarks.";
                return false;
            }

            return true;
        }

        public String getMessage() {
            return message;
        }
    }
}
