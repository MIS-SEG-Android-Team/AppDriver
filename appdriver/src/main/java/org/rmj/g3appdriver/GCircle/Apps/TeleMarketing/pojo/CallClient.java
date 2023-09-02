package org.rmj.g3appdriver.GCircle.Apps.TeleMarketing.pojo;

public class CallClient {

    private final String sMobileNo;
    private final String sFullName;
    private final String sAddressx;

    public CallClient(String MobileNo, String FullName, String Address) {
        this.sMobileNo = MobileNo;
        this.sFullName = FullName;
        this.sAddressx = Address;
    }

    public String getMobileNo() {
        return sMobileNo;
    }

    public String getFullName() {
        return sFullName;
    }

    public String getAddressx() {
        return sAddressx;
    }
}
