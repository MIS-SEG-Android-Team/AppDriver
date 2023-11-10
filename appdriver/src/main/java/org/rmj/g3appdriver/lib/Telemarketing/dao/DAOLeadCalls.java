package org.rmj.g3appdriver.lib.Telemarketing.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.lib.Telemarketing.entities.ELeadCalls;

import java.util.List;

@Dao
public interface DAOLeadCalls {
    @Query("SELECT * FROM Lead_Calls WHERE sTransNox = :sTransNoxx")
    ELeadCalls GetLeadTrans(String sTransNoxx);
    @Query("SELECT lead.sReferNox sReferNox, ccl.sClientNM sClientNm, ccl.xAddressx sAddressx, lead.sMobileNo sMobileNo, " +
            "lead.sReferNox sReferNox, mci.sModelIDx sModelIDx, lead.dTransact dTransact " +
            "FROM Lead_Calls lead " +
            "LEFT JOIN  Call_Client ccl ON (lead.sClientID = ccl.sClientID) " +
            "LEFT JOIN MC_Inquiry mci ON (lead.sReferNox = mci.sTransNox) " +
            "WHERE (lead.cTranStat = '0' " +
            "OR (lead.cTranStat = '1' " +
            "AND lead.sAgentIDx = :sAgentID)) " +
            "AND :sSimClause AND lead.sSourceCd = :sLeadsrc " +
            "ORDER BY dTransact DESC, lead.cSubscrbr DESC, lead.cTranStat DESC LIMIT 1")
    LiveData<LeadInformation> GetLiveLeadCall(String sAgentID, String sSimClause, String sLeadsrc);
    @Query("SELECT lead.sReferNox sReferNox, ccl.sClientNM sClientNm, ccl.xAddressx sAddressx, lead.sMobileNo sMobileNo, " +
            "lead.sReferNox sReferNox, mci.sModelIDx sModelIDx, mci.dFollowUp dFollowUp " +
            "FROM Lead_Calls lead " +
            "LEFT JOIN  Call_Client ccl ON (lead.sClientID = ccl.sClientID) " +
            "LEFT JOIN MC_Inquiry mci ON (lead.sReferNox = mci.sTransNox) " +
            "WHERE (lead.cTranStat = '1' " +
            "AND lead.sAgentIDx = :sAgentID " +
            "AND mci.dFollowUp= :dFollowUp) " +
            "AND :sSimClause " +
            "ORDER BY dFollowUp DESC, lead.cSubscrbr DESC, lead.cTranStat DESC LIMIT 1")
    LiveData<LeadInformation> GetLiveSchedCall(String sAgentID, String sSimClause, String dFollowUp);
    @Query("SELECT lead.sReferNox sReferNox, ccl.sClientNM sClientNm, lead.sMobileNo sMobileNo, lead.dTransact dTransact, " +
            "lead.cTLMStatx cTLMStatx, lead.sRemarksx sRemarksx " +
            "FROM Lead_Calls lead " +
            "LEFT JOIN  Call_Client ccl ON (lead.sClientID = ccl.sClientID) " +
            "ORDER BY lead.dTransact DESC, lead.sMobileNo ASC, ccl.sClientNM ASC")
    LiveData<List<LeadHistory>> GetLeadHistory();
    @Query("UPDATE Lead_Calls SET cTLMStatx= :cTLMStatx WHERE sTransNox= :sTransNoxx")
    void UpdateLeadCall(String sTransNoxx, String cTLMStatx);
    @Insert
    Long SaveLeads(ELeadCalls eLeadCalls);
    @Update
    int UpdateLeads(ELeadCalls eLeadCalls);

    class LeadInformation{
        public String sReferNox;
        public String sClientNm;
        public String sAddressx;
        public String sMobileNo;
        public String sModelIDx;
    }
    class LeadHistory{
        public String sReferNox;
        public String sClientNm;
        public String sMobileNo;
        public String dTransact;
        public String cTLMStatx;
        public String sRemarksx;
    }
}
