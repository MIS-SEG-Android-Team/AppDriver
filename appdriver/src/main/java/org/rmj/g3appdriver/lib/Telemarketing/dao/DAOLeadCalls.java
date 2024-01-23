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
    @Query("SELECT a.sTransNox sTransNox, a.sReferNox sReferNox, a.sSourceCD sSourceCD, a.sClientID sClientID, " +
            "a.sMobileNo sMobileNo, a.cSubscrbr cSubscrbr " +
            "FROM Lead_Calls a, Call_Priorities b " +
            "WHERE a.sSourceCD = b.sSourceCD " +
            "AND (a.cTranStat = '0' " +
            "OR (a.cTranStat = '1' " +
            "AND a.sAgentIDx = :sAgentID)) " +
            "AND a.cSubscrbr IN (:sSim1, :sSim2) " +
            "ORDER BY a.dTransact DESC, a.cSubscrbr DESC, a.cTranStat DESC, b.srcIndex ASC LIMIT 1")
    LiveData<LeadInformation> GetInitLead(String sAgentID, String sSim1, String sSim2);
    @Query("SELECT lead.sReferNox sReferNox, ccl.sClientNM sClientNm, ccl.xAddressx sAddressx, lead.sMobileNo sMobileNo, " +
            "lead.sReferNox sReferNox, mci.sModelIDx sModelIDx, lead.dTransact dTransact " +
            "FROM Lead_Calls lead " +
            "LEFT JOIN  Call_Client ccl ON (lead.sClientID = ccl.sClientID) " +
            "LEFT JOIN MC_Inquiry mci ON (lead.sReferNox = mci.sTransNox) " +
            "WHERE lead.sReferNox = :sTransNox")
    LiveData<LeadDetails> GetLeadDetails(String sTransNox);
    @Query("SELECT lead.sReferNox sReferNox, ccl.sClientNM sClientNm, lead.sMobileNo sMobileNo, lead.dModified dTransact, " +
            "lead.cTLMStatx cTLMStatx, lead.sRemarksx sRemarksx, lead.dCallStrt dCallStrt, lead.dCallEndx dCallEndx, " +
            "mci.dFollowUp dFollowUp " +
            "FROM Lead_Calls lead " +
            "LEFT JOIN  Call_Client ccl ON (lead.sClientID = ccl.sClientID) " +
            "LEFT JOIN MC_Inquiry mci ON (lead.sReferNox = mci.sTransNox) " +
            "WHERE sAgentIDx= :sUserIdxx AND cTranStat = '1' " +
            "ORDER BY lead.dTransact DESC, lead.sMobileNo ASC, ccl.sClientNM ASC")
    LiveData<List<LeadHistory>> GetCallHistory(String sUserIdxx);
    @Query("SELECT " +
            "COUNT(CASE WHEN cTLMStatx = 'NI' THEN sTransNox END) AS nNI, " +
            "COUNT(CASE WHEN cTLMStatx = 'NN' THEN sTransNox END) AS nNN, " +
            "COUNT(CASE WHEN cTLMStatx = 'WN' THEN sTransNox END) AS nWN, " +
            "COUNT(CASE WHEN cTLMStatx = 'UR' THEN sTransNox END) AS nUR, " +
            "COUNT(CASE WHEN cTLMStatx = 'NA' THEN sTransNox END) AS nNA, " +
            "COUNT(CASE WHEN cTLMStatx = 'CB' THEN sTransNox END) AS nCB, " +
            "COUNT(CASE WHEN cTLMStatx = 'AM' THEN sTransNox END) AS nAM, " +
            "COUNT(CASE WHEN cTLMStatx = 'PS' THEN sTransNox END) AS nPS, " +
            "COUNT(CASE WHEN cTLMStatx = 'NC' THEN sTransNox END) AS nNC " +
            "FROM Lead_Calls " +
            "WHERE sAgentIDx= :sUserIdxx")
    LiveData<CountLeads> GetLeadCounts(String sUserIdxx);
    @Query("UPDATE Lead_Calls SET cTLMStatx= :cTLMStatx, cTranStat = :cTranStat, " +
            "sApprovCd = :sApprovCd , dCallStrt = :sCallStrt, " +
            "dCallEndx = :sCallEnd, sModified = :sModified, dModified = :dModified " +
            "WHERE sTransNox= :sTransNoxx")
    int UpdateLeadCall(String sTransNoxx, String cTLMStatx, String cTranStat, String sApprovCd,
                       String sCallStrt, String sCallEnd, String sModified, String dModified);
    @Insert
    Long SaveLeads(ELeadCalls eLeadCalls);
    @Update
    int UpdateLeads(ELeadCalls eLeadCalls);
    @Query("DELETE FROM Lead_Calls")
    int RemoveLeads();

    class LeadInformation{
        public String sTransNox;
        public String sReferNox;
        public String sSourceCD;
        public String sClientID;
        public String sMobileNo;
        public String cSubscrbr;
    }
    class LeadDetails{
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
        public String dCallStrt;
        public String dCallEndx;
        public String dFollowUp;
    }
    class CountLeads{
        public int nNI;
        public int nNN;
        public int nWN;
        public int nUR;
        public int nNA;
        public int nCB;
        public int nAM;
        public int nPS;
        public int nNC;
    }
}
