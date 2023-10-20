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
    private String sTransNox = "";
    @ColumnInfo(name = "sAgentIDx")
    private String sAgentIDx = "";
    @ColumnInfo(name = "dTransact")
    private String dTransact = "";
    @ColumnInfo(name = "sClientID")
    private String sClientID = "";
    @ColumnInfo(name = "sMobileNo")
    private String sMobileNo = "";
    @ColumnInfo(name = "sRemarksx")
    private String sRemarksx = "";
    @ColumnInfo(name = "sReferNox")
    private String sReferNox = "";
    @ColumnInfo(name = "sSourceCD")
    private String sSourceCD = "";
    @ColumnInfo(name = "sApprovCd")
    private String sApprovCd = "";
    @ColumnInfo(name = "cTranStat")
    private String cTranStat = "0";
    @ColumnInfo(name = "dCallStrt")
    private String dCallStrt = "";
    @ColumnInfo(name = "dCallEndx")
    private String dCallEndx = "";
    @ColumnInfo(name = "nNoRetryx")
    private int nNoRetryx = 0;
    @ColumnInfo(name = "cSubscrbr")
    private int cSubscrbr = 0;
    @ColumnInfo(name = "cCallStat")
    private int cCallStat = 0;
    @ColumnInfo(name = "cTLMStatx")
    private int cTLMStatx = 0;
    @ColumnInfo(name = "cSMSStatx")
    private int cSMSStatx = 0;
    @ColumnInfo(name = "nSMSSentx")
    public int nSMSSentx = 0;
    @ColumnInfo(name = "sModified")
    private String sModified = "";
    @ColumnInfo(name = "dModified")
    private String dModified = "";

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

    public int getnNoRetryx() {
        return nNoRetryx;
    }

    public void setnNoRetryx(int nNoRetryx) {
        this.nNoRetryx = nNoRetryx;
    }

    public int getcSubscrbr() {
        return cSubscrbr;
    }

    public void setcSubscrbr(int cSubscrbr) {
        this.cSubscrbr = cSubscrbr;
    }

    public int getcCallStat() {
        return cCallStat;
    }

    public void setcCallStat(int cCallStat) {
        this.cCallStat = cCallStat;
    }

    public int getcTLMStatx() {
        return cTLMStatx;
    }

    public void setcTLMStatx(int cTLMStatx) {
        this.cTLMStatx = cTLMStatx;
    }

    public int getcSMSStatx() {
        return cSMSStatx;
    }

    public void setcSMSStatx(int cSMSStatx) {
        this.cSMSStatx = cSMSStatx;
    }

    public int getnSMSSentx() {
        return nSMSSentx;
    }

    public void setnSMSSentx(int nSMSSentx) {
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
