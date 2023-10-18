package org.rmj.g3appdriver.lib.Telemarketing.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.lib.Telemarketing.entities.EMCInquiry;

import java.util.List;

@Dao
public interface DAOMCInquiry {
    @Query("SELECT * FROM MC_Inquiry WHERE sTransNox= :sTransNox")
    EMCInquiry GetMCInquiry(String sTransNox);
    @Query("SELECT * FROM MC_Inquiry WHERE sTransNox= :sTransNox")
    LiveData<List<EMCInquiry>> GetLiveMCInquiry(String sTransNox);
    @Insert
    void SaveMCInq(EMCInquiry emcInquiry);
    @Update
    void UpdateMCInq(EMCInquiry emcInquiry);
}
