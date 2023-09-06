package org.rmj.g3appdriver.lib.addressbook.pojo;

public class AddressUpdateRequest {

    private String dTransact = "";
    private String sHouseNox = "";
    private String sAddressx = "";
    private String sBrgyIDxx = "";
    private String sTownIDxx = "";

    public AddressUpdateRequest() {
    }

    public String getTransact() {
        return dTransact;
    }

    public void setTransact(String dTransact) {
        this.dTransact = dTransact;
    }

    public String getHouseNo() {
        return sHouseNox;
    }

    public void setHouseNo1(String sHouseNo) {
        this.sHouseNox = sHouseNo;
    }

    public String getAddress() {
        return sAddressx;
    }

    public void setAddress(String sAddressx) {
        this.sAddressx = sAddressx;
    }

    public String getBrgyIDx() {
        return sBrgyIDxx;
    }

    public void setBrgyIDx(String sBrgyIDxx) {
        this.sBrgyIDxx = sBrgyIDxx;
    }

    public String getTownIDx() {
        return sTownIDxx;
    }

    public void setTownIDx(String sTownIDx1) {
        this.sTownIDxx = sTownIDx1;
    }

    public static class AddressUpdateRequestValidator{

        private String message;

        public String getMessage() {
            return message;
        }

        public boolean isDataValid(AddressUpdateRequest loRequest){
            if(loRequest.getTransact().isEmpty()){
                message = "Transaction date not set.";
                return false;
            }
            if(loRequest.getHouseNo().isEmpty()){
                message = "Please enter house no.";
                return false;
            }
            if(loRequest.getBrgyIDx().isEmpty()){
                message = "Please enter barangay.";
                return false;
            }
            if(loRequest.getTownIDx().isEmpty()){
                message = "Please enter municipality or town.";
                return false;
            }

            return true;
        }
    }
}
