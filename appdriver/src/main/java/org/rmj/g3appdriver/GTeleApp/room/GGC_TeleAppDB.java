package org.rmj.g3appdriver.GTeleApp.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.rmj.g3appdriver.GTeleApp.room.DataAccessObject.DModelInfo;
import org.rmj.g3appdriver.GTeleApp.room.DataAccessObject.DProductInquiry;
import org.rmj.g3appdriver.GTeleApp.room.Entities.EMCColor;
import org.rmj.g3appdriver.GTeleApp.room.Entities.EMCModelCashPrice;
import org.rmj.g3appdriver.GTeleApp.room.Entities.EMcBrand;
import org.rmj.g3appdriver.GTeleApp.room.Entities.EMcCategory;
import org.rmj.g3appdriver.GTeleApp.room.Entities.EMcModel;
import org.rmj.g3appdriver.GTeleApp.room.Entities.EMcModelPrice;
import org.rmj.g3appdriver.GTeleApp.room.Entities.EMcTermCategory;

@Database(entities = {
        EMcBrand.class,
        EMcCategory.class,
        EMCColor.class,
        EMcModel.class,
        EMCModelCashPrice.class,
        EMcModelPrice.class,
        EMcTermCategory.class}, version = 1, exportSchema = false)
public abstract class GGC_TeleAppDB extends RoomDatabase {
    private static final String TAG = "GGC_TeleAppDB";

    private static GGC_TeleAppDB instance;

    public abstract DModelInfo modelInfoDao();
    public abstract DProductInquiry productInquiryDao();

    public static synchronized GGC_TeleAppDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GGC_TeleAppDB.class, "GGC_ISysDBF")
                    .allowMainThreadQueries()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static final Callback roomCallBack = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.e(TAG, "Local database has been created.");
        }
    };
}
