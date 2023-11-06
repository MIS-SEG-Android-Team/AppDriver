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
    public String sTransNox;
    @ColumnInfo(name = "dFollowUp")
    public String dFollowUp;
    @ColumnInfo(name = "sClientID")
    public String sClientID;
    @ColumnInfo(name = "sBrandIDx")
    public String sBrandIDx;
    @ColumnInfo(name = "sModelIDx")
    public String sModelIDx;
    @ColumnInfo(name = "sColorIDx")
    public String sColorIDx;
    @ColumnInfo(name = "nTerms")
    public int nTerms;
    @ColumnInfo(name = "dTargetxx")
    public String dTargetxx;
    @ColumnInfo(name = "nDownPaym")
    public double nDownPaym;
    @ColumnInfo(name = "nMonAmort")
    public double nMonAmort;
    @ColumnInfo(name = "nCashPrc")
    public double nCashPrc;
    @ColumnInfo(name = "sRelatnID")
    public String sRelatnID;
    @ColumnInfo(name = "sTableNM")
    public String sTableNM;

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

    public String getsTableNM() {
        return sTableNM;
    }

    public void setsTableNM(String sTableNM) {
        this.sTableNM = sTableNM;
    }
}
