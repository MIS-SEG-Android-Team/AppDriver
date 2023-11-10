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
    @Query("UPDATE Client_Mobile SET dLastCall= :dLastCall, nUnreachx= nUnreachx+ :nUnreachx " +
            "WHERE sClientID= :sClientID AND sMobileNo= :sMobileNo")
    int UpdateCallTrans(String sClientID, String sMobileNo, String dLastCall,int nUnreachx);
    @Insert
    Long SaveClientMobile(EClientMobile eClientMobile);
    @Update
    int UpdateClientMobile(EClientMobile eClientMobile);
}
