package org.rmj.g3appdriver.GTeleApp.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import org.rmj.g3appdriver.GTeleApp.room.Entities.EMCModelCashPrice;
import org.rmj.g3appdriver.GTeleApp.room.Entities.EMcBrand;
import org.rmj.g3appdriver.GTeleApp.room.Entities.EMcModel;

import java.util.List;

@Dao
public interface DProductInquiry {

    LiveData<List<EMcBrand>> GetAllMcBrand();

    LiveData<List<EMcModel>> GetAllMcModel(String fsBrand);

    LiveData<List<EMCModelCashPrice>> GetModelCashPrice(String fsModelID);
}
