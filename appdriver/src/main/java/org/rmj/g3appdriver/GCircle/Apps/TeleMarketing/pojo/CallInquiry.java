package org.rmj.g3appdriver.GCircle.Apps.TeleMarketing.pojo;

import org.json.JSONObject;
import org.rmj.g3appdriver.lib.Inquiry.pojo.InquiryInfo;

public class CallInquiry {

    private String cPaymForm = "";
    private String sBrandIDx = "";
    private String sModelIDx = "";
    private String sColorIDx = "";
    private String sTermIDxx = "";
    private String dTargetxx = "";
    private String nDownPaym = "";
    private Double nCashPrce = 0.0;
    private String dPricexxx = "";


    private String nMonthAmr = "";
    private String sRelatnID = "";

    private String message;

    public CallInquiry() {
    }

    public String getMessage() {
        return message;
    }

    public String getPaymForm() {
        return cPaymForm;
    }

    /**
     *
     * @param cPaymForm
     *          0 = Cash
     *          1 = Installment
     */
    public void setPaymForm(String cPaymForm) {
        this.cPaymForm = cPaymForm;
    }

    public String getBrandIDx() {
        return sBrandIDx;
    }

    public void setBrandIDx(String sBrandIDx) {
        this.sBrandIDx = sBrandIDx;
    }

    public String getModelIDx() {
        return sModelIDx;
    }

    public void setModelIDx(String sModelIDx) {
        this.sModelIDx = sModelIDx;
    }

    public String getColorIDx() {
        return sColorIDx;
    }

    public void setColorIDx(String sColorIDx) {
        this.sColorIDx = sColorIDx;
    }

    public String getTermIDxx() {
        return sTermIDxx;
    }

    public void setTermIDxx(String sTermIDxx) {
        this.sTermIDxx = sTermIDxx;
    }

    public String getTargetxx() {
        return dTargetxx;
    }

    public void setTargetxx(String dTargetxx) {
        this.dTargetxx = dTargetxx;
    }

    public String getDownPaym() {
        return nDownPaym;
    }

    public void setDownPaym(String nDownPaym) {
        this.nDownPaym = nDownPaym;
    }

    public String getnMonthAmr() {
        return nMonthAmr;
    }

    public void setMonthAmr(String nMonthAmr) {
        this.nMonthAmr = nMonthAmr;
    }
    public String getRelatnID() {
        return sRelatnID;
    }

    public void setRelatnID(String sRelatnID) {
        this.sRelatnID = sRelatnID;
    }

    public Double getCashPrce() {
        return nCashPrce;
    }

    public void setCashPrce(Double nCashPrce) {
        this.nCashPrce = nCashPrce;
    }

    public String getPricexxx() {
        return dPricexxx;
    }

    public void setPricexxx(String dPricexxx) {
        this.dPricexxx = dPricexxx;
    }

    public static class CallInquiryValidator{

        private String message;

        public String getMessage() {
            return message;
        }

        public boolean isDataValid(InquiryInfo foVal){
            if(foVal.getGanadoTp().isEmpty()) {
                message = "Please select type of unit whether motorcycle, automobile or mobile phone";
                return false;
            }

            if(foVal.getBrandIDx().isEmpty()) {
                message = "Please select which motorcycle brand you prefer.";
                return false;
            }

            if(foVal.getModelIDx().isEmpty()) {
                message = "Please select which motorcycle unit you prefer.";
                return false;
            }

            if(foVal.getColorIDx().isEmpty()) {
                message = "Please select which color you prefer.";
                return false;
            }

            if(foVal.getTargetxx().isEmpty()){
                message = "Please select the target date for getting the unit.";
                return false;
            }

            if(foVal.getPaymForm().isEmpty()) {
                message = "Please select which type of payment you prefer. Cash or Installment";
                return false;
            }

            if(foVal.getPaymForm().equalsIgnoreCase("1")) {
                if (foVal.getTermIDxx().isEmpty()) {
                    message = "Please select the desired installment term.";
                    return false;
                }
                if (foVal.getDownPaym().isEmpty()) {
                    message = "Please enter your downpayment.";
                    return false;
                }
            }

            return true;
        }
    }

    public static class InquiryJSONGenerator{

        public static String createJSON(CallInquiry foInquiry) throws Exception{
            JSONObject loJson = new JSONObject();
            JSONObject joProdct = new JSONObject();
            joProdct.put("sBrandIDx", foInquiry.getBrandIDx());
            joProdct.put("sModelIDx", foInquiry.getModelIDx());
            joProdct.put("sColorIDx", foInquiry.getColorIDx());
            joProdct.put("nSelPrice", foInquiry.getCashPrce());
            joProdct.put("dPricexxx", foInquiry.getPricexxx());
            loJson.put("sPrdctDtl", joProdct);

            JSONObject joPayment = new JSONObject();
            joPayment.put("sTermIDxx", foInquiry.getTermIDxx());
            joPayment.put("nDownPaym", foInquiry.getDownPaym());
            loJson.put("sPaymntInf", joPayment);

            return loJson.toString();
        }
    }
}
