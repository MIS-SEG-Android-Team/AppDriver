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
    @ColumnInfo(name = "dTransact")
    private String dTransact;
    @ColumnInfo(name = "sClientID")
    private String sClientID;
    @ColumnInfo(name = "sModelIDx")
    private String sModelIDx;
    @ColumnInfo(name = "sColorIDx")
    private String sColorIDx;
    @ColumnInfo(name = "sInquiryx")
    private String sInquiryx;
    @ColumnInfo(name = "dTargetxx")
    private String dTargetxx;
    @ColumnInfo(name = "dPurchase")
    private String dPurchase;
    @ColumnInfo(name = "dFollowUp")
    private String dFollowUp;
    @ColumnInfo(name = "sAgentIDx")
    private String sAgentIDx;
    @ColumnInfo(name = "cPurcType")
    private String cPurcType;
    @ColumnInfo(name = "sRemarks1")
    private String sRemarks1;
    @ColumnInfo(name = "sRemarks2")
    private String sRemarks2;
    @ColumnInfo(name = "sSourceNo")
    private String sSourceNo;
    @ColumnInfo(name = "sCreatedx")
    private String sCreatedx;
    @ColumnInfo(name = "dCreatedx")
    private String dCreatedx;
    @ColumnInfo(name = "cTranStat")
    private String cTranStat;
    @ColumnInfo(name = "sModified")
    private String sModified;
    @ColumnInfo(name = "dModified")
    private String dModified;

    public String getsTransNox() {
        return sTransNox;
    }

    public void setsTransNox(String sTransNox) {
        this.sTransNox = sTransNox;
    }

    public String getdTransact() {
        return dTransact;
    }

    public void setdTransact(String dTransact) {
        this.dTransact = dTransact;
    }

    public String getsClientID() {
        return sClientID;
    }

    public void setsClientID(String sClientID) {
        this.sClientID = sClientID;
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

    public String getsInquiryx() {
        return sInquiryx;
    }

    public void setsInquiryx(String sInquiryx) {
        this.sInquiryx = sInquiryx;
    }

    public String getdTargetxx() {
        return dTargetxx;
    }

    public void setdTargetxx(String dTargetxx) {
        this.dTargetxx = dTargetxx;
    }

    public String getdPurchase() {
        return dPurchase;
    }

    public void setdPurchase(String dPurchase) {
        this.dPurchase = dPurchase;
    }

    public String getdFollowUp() {
        return dFollowUp;
    }

    public void setdFollowUp(String dFollowUp) {
        this.dFollowUp = dFollowUp;
    }

    public String getsAgentIDx() {
        return sAgentIDx;
    }

    public void setsAgentIDx(String sAgentIDx) {
        this.sAgentIDx = sAgentIDx;
    }

    public String getcPurcType() {
        return cPurcType;
    }

    public void setcPurcType(String cPurcType) {
        this.cPurcType = cPurcType;
    }

    public String getsRemarks1() {
        return sRemarks1;
    }

    public void setsRemarks1(String sRemarks1) {
        this.sRemarks1 = sRemarks1;
    }

    public String getsRemarks2() {
        return sRemarks2;
    }

    public void setsRemarks2(String sRemarks2) {
        this.sRemarks2 = sRemarks2;
    }

    public String getsSourceNo() {
        return sSourceNo;
    }

    public void setsSourceNo(String sSourceNo) {
        this.sSourceNo = sSourceNo;
    }

    public String getsCreatedx() {
        return sCreatedx;
    }

    public void setsCreatedx(String sCreatedx) {
        this.sCreatedx = sCreatedx;
    }

    public String getdCreatedx() {
        return dCreatedx;
    }

    public void setdCreatedx(String dCreatedx) {
        this.dCreatedx = dCreatedx;
    }

    public String getcTranStat() {
        return cTranStat;
    }

    public void setcTranStat(String cTranStat) {
        this.cTranStat = cTranStat;
    }

    public String getsModified() {
        return sModified;
    }

    public void setsModified(String sModified) {
        this.sModified = sModified;
    }

    public String getdModified() {
        return dModified;
    }

    public void setdModified(String dModified) {
        this.dModified = dModified;
    }
}
