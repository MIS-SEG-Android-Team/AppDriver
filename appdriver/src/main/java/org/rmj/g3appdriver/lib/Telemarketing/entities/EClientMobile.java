package org.rmj.g3appdriver.lib.Telemarketing.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Client_Mobile")
public class EClientMobile {
    @ColumnInfo(name = "sClientID")
    private String sClientID;
    @ColumnInfo(name = "sMobileNo")
    private String sMobileNo;
    @ColumnInfo(name = "nEntryNox")
    private int nEntryNox;
    @ColumnInfo(name = "nPriority")
    private int nPriority;
    @ColumnInfo(name = "cIncdMktg")
    private String cIncdMktg;
    @ColumnInfo(name = "nUnreachx")
    private int nUnreachx;
    @ColumnInfo(name = "dLastVeri")
    private String dLastVeri;
    @ColumnInfo(name = "dInactive")
    private String dInactive;
    @ColumnInfo(name = "nNoRetryx")
    private int nNoRetryx;
    @ColumnInfo(name = "cInvalidx")
    private String cInvalidx;
    @ColumnInfo(name = "sIdleTime")
    private String sIdleTime;
    @ColumnInfo(name = "cConfirmd")
    private String cConfirmd;
    @ColumnInfo(name = "dConfirmd")
    private String dConfirmd;
    @ColumnInfo(name = "cSubscr")
    private String cSubscr;
    @ColumnInfo(name = "dHoldMktg")
    private String dHoldMktg;
    @ColumnInfo(name = "dLastCall")
    private String dLastCall;
    @ColumnInfo(name = "cRecdStat")
    private String cRecdStat;

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

    public int getnEntryNox() {
        return nEntryNox;
    }

    public void setnEntryNox(int nEntryNox) {
        this.nEntryNox = nEntryNox;
    }

    public int getnPriority() {
        return nPriority;
    }

    public void setnPriority(int nPriority) {
        this.nPriority = nPriority;
    }

    public String getcIncdMktg() {
        return cIncdMktg;
    }

    public void setcIncdMktg(String cIncdMktg) {
        this.cIncdMktg = cIncdMktg;
    }

    public int getnUnreachx() {
        return nUnreachx;
    }

    public void setnUnreachx(int nUnreachx) {
        this.nUnreachx = nUnreachx;
    }

    public String getdLastVeri() {
        return dLastVeri;
    }

    public void setdLastVeri(String dLastVeri) {
        this.dLastVeri = dLastVeri;
    }

    public String getdInactive() {
        return dInactive;
    }

    public void setdInactive(String dInactive) {
        this.dInactive = dInactive;
    }

    public int getnNoRetryx() {
        return nNoRetryx;
    }

    public void setnNoRetryx(int nNoRetryx) {
        this.nNoRetryx = nNoRetryx;
    }

    public String getcInvalidx() {
        return cInvalidx;
    }

    public void setcInvalidx(String cInvalidx) {
        this.cInvalidx = cInvalidx;
    }

    public String getsIdleTime() {
        return sIdleTime;
    }

    public void setsIdleTime(String sIdleTime) {
        this.sIdleTime = sIdleTime;
    }

    public String getcConfirmd() {
        return cConfirmd;
    }

    public void setcConfirmd(String cConfirmd) {
        this.cConfirmd = cConfirmd;
    }

    public String getdConfirmd() {
        return dConfirmd;
    }

    public void setdConfirmd(String dConfirmd) {
        this.dConfirmd = dConfirmd;
    }

    public String getcSubscr() {
        return cSubscr;
    }

    public void setcSubscr(String cSubscr) {
        this.cSubscr = cSubscr;
    }

    public String getdHoldMktg() {
        return dHoldMktg;
    }

    public void setdHoldMktg(String dHoldMktg) {
        this.dHoldMktg = dHoldMktg;
    }

    public String getdLastCall() {
        return dLastCall;
    }

    public void setdLastCall(String dLastCall) {
        this.dLastCall = dLastCall;
    }

    public String getcRecdStat() {
        return cRecdStat;
    }

    public void setcRecdStat(String cRecdStat) {
        this.cRecdStat = cRecdStat;
    }
}
