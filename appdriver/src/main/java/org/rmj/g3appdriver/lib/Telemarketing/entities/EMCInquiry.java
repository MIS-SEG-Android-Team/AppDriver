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
    public Integer nTerms;
    @ColumnInfo(name = "dTargetxx")
    public String dTargetxx;
    @ColumnInfo(name = "nDownPaym")
    public Double nDownPaym;
    @ColumnInfo(name = "nMonAmort")
    public Double nMonAmort;
    @ColumnInfo(name = "nCashPrc")
    public Double nCashPrc;
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

    public Integer getnTerms() {
        return nTerms;
    }

    public void setnTerms(Integer nTerms) {
        this.nTerms = nTerms;
    }

    public String getdTargetxx() {
        return dTargetxx;
    }

    public void setdTargetxx(String dTargetxx) {
        this.dTargetxx = dTargetxx;
    }

    public Double getnDownPaym() {
        return nDownPaym;
    }

    public void setnDownPaym(Double nDownPaym) {
        this.nDownPaym = nDownPaym;
    }

    public Double getnMonAmort() {
        return nMonAmort;
    }

    public void setnMonAmort(Double nMonAmort) {
        this.nMonAmort = nMonAmort;
    }

    public Double getnCashPrc() {
        return nCashPrc;
    }

    public void setnCashPrc(Double nCashPrc) {
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
