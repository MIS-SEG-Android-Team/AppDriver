package org.rmj.g3appdriver.lib.Telemarketing.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.lib.Telemarketing.entities.EClientMobile;

@Dao
public interface DAOClientMobile {
    @Query("SELECT * FROM Client_Mobile WHERE sClientID= :sClientID AND sMobileNo= :sMobileNo")
    EClientMobile GetClientMobile(String sClientID, String sMobileNo);
    @Query("SELECT * FROM Client_Mobile WHERE sClientID= :sClientID AND sMobileNo= :sMobileNo")
    EClientMobile GetLiveClientMobile(String sClientID, String sMobileNo);
    @Query("SELECT nUnreachx FROM Client_Mobile WHERE sClientID= :sClientID AND sMobileNo= :sMobileNo")
    int CountUnreachx(String sClientID, String sMobileNo);
    @Query("UPDATE Client_Mobile SET dLastCall= :dLastCall,nUnreachx= :nUnreachx " +
            "WHERE sClientID= :sClientID AND sMobileNo= :sMobileNo")
    void UpdateClientMobile(String sClientID, String sMobileNo, String dLastCall,int nUnreachx);
    @Insert
    void SaveClientMobile(EClientMobile eClientMobile);
    @Update
    void UpdateClientMobile(EClientMobile eClientMobile);
}
