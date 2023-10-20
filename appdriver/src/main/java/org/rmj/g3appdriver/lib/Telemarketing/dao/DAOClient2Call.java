package org.rmj.g3appdriver.lib.Telemarketing.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.lib.Telemarketing.entities.EClient2Call;

import java.util.List;

@Dao
public interface DAOClient2Call {
    @Query("SELECT * FROM Call_Client WHERE sClientID = :sClientID")
    EClient2Call GetClientCall(String sClientID);
    @Query("SELECT * FROM Call_Client WHERE sClientID = :sClientID")
    LiveData<List<EClient2Call>> GetLiveClientInfo(String sClientID);
    @Insert
    void SaveClients(EClient2Call eClient2Call);
    @Update
    void UpdateClients(EClient2Call eClient2Call);
}
