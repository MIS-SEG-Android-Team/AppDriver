package org.rmj.g3appdriver.GTeleApp.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import org.rmj.g3appdriver.GTeleApp.room.Entities.EMCModelCashPrice;
import org.rmj.g3appdriver.GTeleApp.room.Entities.EMcBrand;
import org.rmj.g3appdriver.GTeleApp.room.Entities.EMcModel;

import java.util.List;

@Dao
public interface DProductInquiry {

    @Query("SELECT * FROM MC_Brand WHERE cRecdStat == '1'")
    LiveData<List<EMcBrand>> GetAllMcBrand();

    @Query("SELECT * FROM Mc_Model WHERE sBrandIDx =:fsBrand AND cRecdStat == '1'")
    LiveData<List<EMcModel>> GetAllMcModel(String fsBrand);

//    @Query("SELECT * FROM MC_MODE WHERE cRecdStat == '1'")
//    LiveData<List<EMCModelCashPrice>> GetModelCashPrice(String fsModelID);
}
