package org.rmj.g3appdriver.lib.Telemarketing.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MC_Inquiry")
public class EMCInquiry {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String sTransNox;
    @ColumnInfo(name = "dFollowUp")
    private String dFollowUp;
    @ColumnInfo(name = "sClientID")
    private String sClientID;
    @ColumnInfo(name = "sBrandIDx")
    private String sBrandIDx;
    @ColumnInfo(name = "sModelIDx")
    private String sModelIDx;
    @ColumnInfo(name = "sColorIDx")
    private String sColorIDx;
    @ColumnInfo(name = "nTerms")
    private int nTerms;
    @ColumnInfo(name = "dTargetxx")
    private String dTargetxx;
    @ColumnInfo(name = "nDownPaym")
    private double nDownPaym;
    @ColumnInfo(name = "nMonAmort")
    private double nMonAmort;
    @ColumnInfo(name = "nCashPrc")
    private double nCashPrc;
    @ColumnInfo(name = "sRelatnID")
    private String sRelatnID;

    @NonNull
    public String getsTransNox() {
        return sTransNox;
    }

    public void setsTransNox(@NonNull String sTransNox) {
        this.sTransNox = sTransNox;
    }

    public String getdFollowUp() {
        return dFollowUp;
    }

    public void setdFollowUp(String dFollowUp) {
        this.dFollowUp = dFollowUp;
    }

    public String getsClientID() {
        return sClientID;
    }

    public void setsClientID(String sClientID) {
        this.sClientID = sClientID;
    }

    public String getsBrandIDx() {
        return sBrandIDx;
    }

    public void setsBrandIDx(String sBrandIDx) {
        this.sBrandIDx = sBrandIDx;
    }

    public String getsModelIDx() {
        return sModelIDx;
    }

    public void setsModelIDx(String sModelIDx) {
        this.sModelIDx = sModelIDx;
    }

    public String getsColorIDx() {
        return sColorIDx;
    }

    public void setsColorIDx(String sColorIDx) {
        this.sColorIDx = sColorIDx;
    }

    public int getnTerms() {
        return nTerms;
    }

    public void setnTerms(int nTerms) {
        this.nTerms = nTerms;
    }

    public String getdTargetxx() {
        return dTargetxx;
    }

    public void setdTargetxx(String dTargetxx) {
        this.dTargetxx = dTargetxx;
    }

    public double getnDownPaym() {
        return nDownPaym;
    }

    public void setnDownPaym(double nDownPaym) {
        this.nDownPaym = nDownPaym;
    }

    public double getnMonAmort() {
        return nMonAmort;
    }

    public void setnMonAmort(double nMonAmort) {
        this.nMonAmort = nMonAmort;
    }

    public double getnCashPrc() {
        return nCashPrc;
    }

    public void setnCashPrc(double nCashPrc) {
        this.nCashPrc = nCashPrc;
    }

    public String getsRelatnID() {
        return sRelatnID;
    }

    public void setsRelatnID(String sRelatnID) {
        this.sRelatnID = sRelatnID;
    }
}
