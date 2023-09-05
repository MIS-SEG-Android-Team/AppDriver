package org.rmj.g3appdriver.GCircle.ImportData.Obj;

import android.app.Application;

import org.rmj.g3appdriver.GCircle.ImportData.model.ImportDataCallback;
import org.rmj.g3appdriver.GCircle.ImportData.model.ImportInstance;
import org.rmj.g3appdriver.lib.ProductInquiry.data.repository.MCModel;

public class Import_McColors implements ImportInstance {
    public static final String TAG = ImportBrandModel.class.getSimpleName();

    private final MCModel poSys;

    public Import_McColors(Application instance) {
        this.poSys = new MCModel(instance);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        if(!poSys.ImportModelColor()){
            callback.OnFailedImportData(poSys.getMessage());
        } else {
            callback.OnSuccessImportData();
        }
    }
}
