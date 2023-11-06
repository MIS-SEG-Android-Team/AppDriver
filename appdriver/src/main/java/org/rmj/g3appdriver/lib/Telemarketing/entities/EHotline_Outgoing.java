package org.rmj.g3appdriver.lib.Telemarketing.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Hotline_Outgoing")
public class EHotline_Outgoing {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    public String sTransNox;
    @ColumnInfo(name = "dTransact")
    public String dTransact;
    @ColumnInfo(name = "sDivision")
    public String sDivision;
    @ColumnInfo(name = "sMobileNo")
    public String sMobileNo;
    @ColumnInfo(name = "sMessagex")
    public String sMessagex;
    @ColumnInfo(name = "cSubscrbr")
    public String cSubscrbr;
    @ColumnInfo(name = "dDueUntil")
    public String dDueUntil;
    @ColumnInfo(name = "cSendStat")
    public String cSendStat;
    @ColumnInfo(name = "nNoRetryx")
    public int nNoRetryx;
    @ColumnInfo(name = "sUDHeader")
    public String sUDHeader;
    @ColumnInfo(name = "sReferNox")
    public String sReferNox;
    @ColumnInfo(name = "sSourceCd")
    public String sSourceCd;
    @ColumnInfo(name = "cTranStat")
    public String cTranStat;
    @ColumnInfo(name = "nPriority")
    public int nPriority;
    @ColumnInfo(name = "sModified")
    public String sModified;
    @ColumnInfo(name = "dModified")
    public String dModified;

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
