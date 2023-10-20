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
    @Query("SELECT * FROM Lead_Calls " +
            "WHERE (cTranStat = 0 OR (cTranStat = 1 AND sAgentIDx = :sAgentID))" +
            "AND :sSimClause AND sSourceCd = :sLeadsrc " +
            "ORDER BY dTransact ASC, cTranStat DESC, sAgentIDx DESC, sTransNox ASC")
    LiveData<List<ELeadCalls>> GetLiveLeadCalls(String sAgentID, String sSimClause, String sLeadsrc);
    @Query("SELECT COUNT(*) FROM Lead_Calls WHERE sTransNox = :sTransNoxx")
    int CountLeadTrans(String sTransNoxx);
    @Insert
    void SaveLeads(ELeadCalls eLeadCalls);
    @Update
    void UpdateLeads(ELeadCalls eLeadCalls);
}
