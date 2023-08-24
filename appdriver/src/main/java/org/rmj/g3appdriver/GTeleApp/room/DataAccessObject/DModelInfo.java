package org.rmj.g3appdriver.GTeleApp.room.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GTeleApp.room.Entities.EMcBrand;
import org.rmj.g3appdriver.GTeleApp.room.Entities.EMcModel;


@Dao
public interface DModelInfo {

    @Insert
    void Save(EMcBrand foVal);

    @Query("SELECT * FROM MC_Brand ORDER BY dTimeStmp DESC LIMIT 1")
    EMcBrand GetLatestBrand();

    @Insert
    void Save(EMcModel foVal);

    @Query("SELECT * FROM Mc_Model ORDER BY dTimeStmp DESC LIMIT 1")
    EMcModel GetLatestModel();


}
