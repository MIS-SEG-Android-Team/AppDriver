package org.rmj.g3appdriver.lib.Telemarketing.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.lib.Telemarketing.entities.ELeadCalls;
@Dao
public interface DAOLeadCalls {
    @Query("SELECT * FROM Lead_Calls WHERE sTransNox = :sTransNoxx")
    ELeadCalls GetLeadTrans(String sTransNoxx);
    @Query("SELECT COUNT(*) FROM Lead_Calls " +
            "WHERE (cTranStat = 0 OR (cTranStat = 1 AND sAgentIDx = :sAgentID))" +
            "AND :sSimClause AND sSourceCd = :sLeadsrc ")
    int CountLeads(String sAgentID, String sSimClause, String sLeadsrc);
    @Query("SELECT ccl.sClientNM sClientNm, ccl.xAddressx sAddressx, lead.sMobileNo sMobileNo, " +
            "lead.sReferNox sReferNox, mci.sModelIDx sModelIDx, " +
            "CASE " +
            "WHEN lead.cTranStat = '0' THEN lead.dTransact " +
            "ELSE mci.dFollowUp " +
            "END dTransaction " +
            "FROM Lead_Calls lead " +
            "LEFT JOIN  Call_Client ccl ON (lead.sClientID = ccl.sClientID) " +
            "LEFT JOIN MC_Inquiry mci ON (lead.sReferNox = mci.sTransNox) " +
            "WHERE (lead.cTranStat = '0' " +
            "OR (lead.cTranStat = '1' " +
            "AND lead.sAgentIDx = :sAgentID " +
            "AND mci.dFollowUp = :dToday)) " +
            "AND :sSimClause AND lead.sSourceCd = :sLeadsrc " +
            "ORDER BY dTransaction DESC, lead.cSubscrbr DESC, lead.cTranStat DESC LIMIT 1")
    LiveData<LeadInformation> GetLiveLeadCall(String sAgentID, String sSimClause, String sLeadsrc, String dToday);
    @Query("UPDATE Lead_Calls SET cTLMStatx= :cTLMStatx WHERE sTransNox= :sTransNoxx")
    void UpdateLeadCall(String sTransNoxx, String cTLMStatx);
    @Insert
    void SaveLeads(ELeadCalls eLeadCalls);
    @Update
    void UpdateLeads(ELeadCalls eLeadCalls);

    class LeadInformation{
        public String sReferNox;
        public String sClientNm;
        public String sAddressx;
        public String sMobileNo;
        public String sModelIDx;
    }
}
