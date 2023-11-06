package org.rmj.g3appdriver.lib.Telemarketing.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.persistence.UniqueConstraint;

@Entity(tableName = "Client_Mobile", primaryKeys = {"sClientID"})
public class EClientMobile {
    @NonNull
    @ColumnInfo(name = "sClientID")
    public String sClientID;
    @NonNull
    @ColumnInfo(name = "sMobileNo")
    public String sMobileNo;
    @ColumnInfo(name = "nEntryNox")
    public int nEntryNox;
    @ColumnInfo(name = "nPriority")
    public int nPriority;
    @ColumnInfo(name = "cIncdMktg")
    public String cIncdMktg;
    @ColumnInfo(name = "nUnreachx")
    public int nUnreachx;
    @ColumnInfo(name = "dLastVeri")
    public String dLastVeri;
    @ColumnInfo(name = "dInactive")
    public String dInactive;
    @ColumnInfo(name = "nNoRetryx")
    public int nNoRetryx;
    @ColumnInfo(name = "cInvalidx")
    public String cInvalidx;
    @ColumnInfo(name = "sIdleTime")
    public String sIdleTime;
    @ColumnInfo(name = "cConfirmd")
    public String cConfirmd;
    @ColumnInfo(name = "dConfirmd")
    public String dConfirmd;
    @ColumnInfo(name = "cSubscr")
    public String cSubscr;
    @ColumnInfo(name = "dHoldMktg")
    public String dHoldMktg;
    @ColumnInfo(name = "dLastCall")
    public String dLastCall;
    @ColumnInfo(name = "cRecdStat")
    public String cRecdStat;

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
