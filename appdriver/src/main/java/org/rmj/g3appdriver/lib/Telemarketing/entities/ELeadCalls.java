package org.rmj.g3appdriver.lib.Telemarketing.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Lead_Calls")
public class ELeadCalls {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    public String sTransNox = "";
    @ColumnInfo(name = "sAgentIDx")
    public String sAgentIDx;
    @ColumnInfo(name = "dTransact")
    public String dTransact;
    @ColumnInfo(name = "sClientID")
    public String sClientID;
    @ColumnInfo(name = "sMobileNo")
    public String sMobileNo;
    @ColumnInfo(name = "sRemarksx")
    public String sRemarksx;
    @ColumnInfo(name = "sReferNox")
    public String sReferNox;
    @ColumnInfo(name = "sSourceCD")
    public String sSourceCD;
    @ColumnInfo(name = "sApprovCd")
    public String sApprovCd;
    @ColumnInfo(name = "cTranStat")
    public String cTranStat;
    @ColumnInfo(name = "dCallStrt")
    public String dCallStrt;
    @ColumnInfo(name = "dCallEndx")
    public String dCallEndx;
    @ColumnInfo(name = "nNoRetryx")
    public Integer nNoRetryx;
    @ColumnInfo(name = "cSubscrbr")
    public String cSubscrbr;
    @ColumnInfo(name = "cCallStat")
    public String cCallStat;
    @ColumnInfo(name = "cTLMStatx")
    public String cTLMStatx;
    @ColumnInfo(name = "cSMSStatx")
    public String cSMSStatx;
    @ColumnInfo(name = "nSMSSentx")
    public Integer nSMSSentx;
    @ColumnInfo(name = "sModified")
    public String sModified;
    @ColumnInfo(name = "dModified")
    public String dModified;

    public ELeadCalls(){}

    @NonNull
    public String getsTransNox() {
        return sTransNox;
    }

    public void setsTransNox(@NonNull String sTransNox) {
        this.sTransNox = sTransNox;
    }

    public String getsAgentIDx() {
        return sAgentIDx;
    }

    public void setsAgentIDx(String sAgentIDx) {
        this.sAgentIDx = sAgentIDx;
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

    public String getsMobileNo() {
        return sMobileNo;
    }

    public void setsMobileNo(String sMobileNo) {
        this.sMobileNo = sMobileNo;
    }

    public String getsRemarksx() {
        return sRemarksx;
    }

    public void setsRemarksx(String sRemarksx) {
        this.sRemarksx = sRemarksx;
    }

    public String getsReferNox() {
        return sReferNox;
    }

    public void setsReferNox(String sReferNox) {
        this.sReferNox = sReferNox;
    }

    public String getsSourceCD() {
        return sSourceCD;
    }

    public void setsSourceCD(String sSourceCD) {
        this.sSourceCD = sSourceCD;
    }

    public String getsApprovCd() {
        return sApprovCd;
    }

    public void setsApprovCd(String sApprovCd) {
        this.sApprovCd = sApprovCd;
    }

    public String getcTranStat() {
        return cTranStat;
    }

    public void setcTranStat(String cTranStat) {
        this.cTranStat = cTranStat;
    }

    public String getdCallStrt() {
        return dCallStrt;
    }

    public void setdCallStrt(String dCallStrt) {
        this.dCallStrt = dCallStrt;
    }

    public String getdCallEndx() {
        return dCallEndx;
    }

    public void setdCallEndx(String dCallEndx) {
        this.dCallEndx = dCallEndx;
    }

    public Integer getnNoRetryx() {
        return nNoRetryx;
    }

    public void setnNoRetryx(Integer nNoRetryx) {
        this.nNoRetryx = nNoRetryx;
    }

    public String getcSubscrbr() {
        return cSubscrbr;
    }

    public void setcSubscrbr(String cSubscrbr) {
        this.cSubscrbr = cSubscrbr;
    }

    public String getcCallStat() {
        return cCallStat;
    }

    public void setcCallStat(String cCallStat) {
        this.cCallStat = cCallStat;
    }

    public String getcTLMStatx() {
        return cTLMStatx;
    }

    public void setcTLMStatx(String cTLMStatx) {
        this.cTLMStatx = cTLMStatx;
    }

    public String getcSMSStatx() {
        return cSMSStatx;
    }

    public void setcSMSStatx(String cSMSStatx) {
        this.cSMSStatx = cSMSStatx;
    }

    public Integer getnSMSSentx() {
        return nSMSSentx;
    }

    public void setnSMSSentx(Integer nSMSSentx) {
        this.nSMSSentx = nSMSSentx;
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
