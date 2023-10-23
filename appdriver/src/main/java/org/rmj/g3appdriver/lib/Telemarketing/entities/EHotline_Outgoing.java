package org.rmj.g3appdriver.lib.Telemarketing.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Hotline_Outgoing")
public class EHotline_Outgoing {
    @ColumnInfo(name = "sTransNox")
    private String sTransNox;
    @ColumnInfo(name = "dTransact")
    private String dTransact;
    @ColumnInfo(name = "sDivision")
    private String sDivision;
    @ColumnInfo(name = "sMobileNo")
    private String sMobileNo;
    @ColumnInfo(name = "sMessagex")
    private String sMessagex;
    @ColumnInfo(name = "cSubscrbr")
    private String cSubscrbr;
    @ColumnInfo(name = "dDueUntil")
    private String dDueUntil;
    @ColumnInfo(name = "cSendStat")
    private String cSendStat;
    @ColumnInfo(name = "nNoRetryx")
    private int nNoRetryx;
    @ColumnInfo(name = "sUDHeader")
    private String sUDHeader;
    @ColumnInfo(name = "sReferNox")
    private String sReferNox;
    @ColumnInfo(name = "sSourceCd")
    private String sSourceCd;
    @ColumnInfo(name = "cTranStat")
    private String cTranStat;
    @ColumnInfo(name = "nPriority")
    private int nPriority;
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

    public String getsDivision() {
        return sDivision;
    }

    public void setsDivision(String sDivision) {
        this.sDivision = sDivision;
    }

    public String getsMobileNo() {
        return sMobileNo;
    }

    public void setsMobileNo(String sMobileNo) {
        this.sMobileNo = sMobileNo;
    }

    public String getsMessagex() {
        return sMessagex;
    }

    public void setsMessagex(String sMessagex) {
        this.sMessagex = sMessagex;
    }

    public String getcSubscrbr() {
        return cSubscrbr;
    }

    public void setcSubscrbr(String cSubscrbr) {
        this.cSubscrbr = cSubscrbr;
    }

    public String getdDueUntil() {
        return dDueUntil;
    }

    public void setdDueUntil(String dDueUntil) {
        this.dDueUntil = dDueUntil;
    }

    public String getcSendStat() {
        return cSendStat;
    }

    public void setcSendStat(String cSendStat) {
        this.cSendStat = cSendStat;
    }

    public int getnNoRetryx() {
        return nNoRetryx;
    }

    public void setnNoRetryx(int nNoRetryx) {
        this.nNoRetryx = nNoRetryx;
    }

    public String getsUDHeader() {
        return sUDHeader;
    }

    public void setsUDHeader(String sUDHeader) {
        this.sUDHeader = sUDHeader;
    }

    public String getsReferNox() {
        return sReferNox;
    }

    public void setsReferNox(String sReferNox) {
        this.sReferNox = sReferNox;
    }

    public String getsSourceCd() {
        return sSourceCd;
    }

    public void setsSourceCd(String sSourceCd) {
        this.sSourceCd = sSourceCd;
    }

    public String getcTranStat() {
        return cTranStat;
    }

    public void setcTranStat(String cTranStat) {
        this.cTranStat = cTranStat;
    }

    public int getnPriority() {
        return nPriority;
    }

    public void setnPriority(int nPriority) {
        this.nPriority = nPriority;
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
