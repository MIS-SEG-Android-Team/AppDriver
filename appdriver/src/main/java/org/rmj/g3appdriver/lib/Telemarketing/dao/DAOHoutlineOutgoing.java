package org.rmj.g3appdriver.lib.Telemarketing.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.lib.Telemarketing.entities.EHotline_Outgoing;

import java.util.List;

@Dao
public interface DAOHoutlineOutgoing {
    @Query("SELECT * FROM Hotline_Outgoing WHERE sTransNox= :sTransNox")
    EHotline_Outgoing GetHotlineOutgoing(String sTransNox);
    @Query("SELECT * FROM Hotline_Outgoing WHERE sTransNox= :sTransNox")
    LiveData<List<EHotline_Outgoing>> GetLiveHotlineOutgoing(String sTransNox);
    @Insert
    Long SaveHotlineOutgoing(EHotline_Outgoing eHotlineOutgoing);
    @Update
    int UpdateHotlineOutgoing(EHotline_Outgoing eHotlineOutgoing);
    @Query("DELETE FROM Hotline_Outgoing")
    int RemoveHOutgoing();
}
