package org.rmj.g3appdriver.lib.Telemarketing.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.lib.Telemarketing.entities.EPriorities;

@Dao
public interface DAOPriorities {
    @Query("SELECT * FROM Call_Priorities WHERE srcIndex = :index")
    EPriorities GetByIndex(int index);
    @Query("SELECT * FROM Call_Priorities WHERE sSourceCD = :sSourceCD")
    EPriorities GetBySource(String sSourceCD);
    @Insert
    Long SavePriorities(EPriorities ePriorities);
    @Update
    int UpdatePriorities(EPriorities ePriorities);
    @Query("DELETE FROM Call_Priorities")
    int RemovePriorities();
}
