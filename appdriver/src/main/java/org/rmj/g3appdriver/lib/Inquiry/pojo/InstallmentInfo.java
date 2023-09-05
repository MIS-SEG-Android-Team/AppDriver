package org.rmj.g3appdriver.lib.Inquiry.pojo;

public class InstallmentInfo {
    private double nMinDownx = 0;
    private double nAmortztn = 0;

    public InstallmentInfo(double nMinDownx, double nAmortztn) {
        this.nMinDownx = nMinDownx;
        this.nAmortztn = nAmortztn;
    }

    public double getMinimumDownpayment() {
        return nMinDownx;
    }

    public double getMonthlyAmortization() {
        return nAmortztn;
    }
}
