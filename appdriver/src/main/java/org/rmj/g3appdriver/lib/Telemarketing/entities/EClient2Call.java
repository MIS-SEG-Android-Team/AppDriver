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
    public String sClientID;
    @ColumnInfo(name = "sClientNM")
    public String sClientNM;
    @ColumnInfo(name = "xAddressx")
    public String xAddressx;
    @ColumnInfo(name = "sPhoneNox")
    public String sPhoneNox;
    @ColumnInfo(name = "sMobileNox")
    public String sMobileNox;

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
