package org.rmj.g3appdriver.lib.Telemarketing.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Call_Client")
public class EClient2Call {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sClientID")
    private String sClientID;
    @ColumnInfo(name = "sClientNM")
    private String sClientNM;
    @ColumnInfo(name = "xAddressx")
    private String xAddressx;
    @ColumnInfo(name = "sPhoneNox")
    private String sPhoneNox;
    @ColumnInfo(name = "sMobileNox")
    private String sMobileNox;

    @NonNull
    public String getsClientID() {
        return sClientID;
    }

    public void setsClientID(@NonNull String sClientID) {
        this.sClientID = sClientID;
    }

    public String getsClientNM() {
        return sClientNM;
    }

    public void setsClientNM(String sClientNM) {
        this.sClientNM = sClientNM;
    }

    public String getxAddressx() {
        return xAddressx;
    }

    public void setxAddressx(String xAddressx) {
        this.xAddressx = xAddressx;
    }

    public String getsPhoneNox() {
        return sPhoneNox;
    }

    public void setsPhoneNox(String sPhoneNox) {
        this.sPhoneNox = sPhoneNox;
    }

    public String getsMobileNox() {
        return sMobileNox;
    }

    public void setsMobileNox(String sMobileNox) {
        this.sMobileNox = sMobileNox;
    }
}
