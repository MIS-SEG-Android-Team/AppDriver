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
    EClient2Call GetClient2Call(String sClientID);
    @Insert
    Long SaveClients(EClient2Call eClient2Call);
    @Update
    int UpdateClients(EClient2Call eClient2Call);
}
