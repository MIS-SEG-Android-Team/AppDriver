package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.lib.Notifications.data.Entity.ENotificationMaster;
import org.rmj.g3appdriver.lib.Notifications.data.Entity.ENotificationRecipient;
import org.rmj.g3appdriver.lib.Notifications.data.Entity.ENotificationUser;

@Dao
public interface DGCNNotificationRcv {

    @Insert
    void insert(ENotificationMaster notificationMaster);

    @Insert
    void insert(ENotificationRecipient notificationRecipient);

    @Insert
    void insert(ENotificationUser notificationUser);

    @Update
    void update(ENotificationMaster notificationMaster);

    @Update
    void update(ENotificationRecipient notificationRecipient);

    @Update
    void update(ENotificationUser notificationUser);

    @Query("SELECT COUNT(*) FROM Notification_Info_Master WHERE sMesgIDxx=:TransNox")
    int CheckNotificationIfExist(String TransNox);


    @Query("UPDATE Notification_Info_Recepient SET cMesgStat =:status WHERE sTransNox =:MessageID")
    void updateNotificationStatusFromOtherDevice(String MessageID, String status);

    @Query("SELECT * FROM Notification_User WHERE sUserIDxx=:fsVal")
    ENotificationUser CheckIfUserExist(String fsVal);


    @Query("UPDATE Notification_Info_Recepient SET " +
            "dLastUpdt =:dateTime, " +
            "dReceived =:dateTime, " +
            "cMesgStat =:Status, " +
            "cStatSent = '1' " +
            "WHERE sTransNox =:MessageID")
    void UpdateSentResponseStatus(String MessageID, String Status, String dateTime);

    @Query("SELECT * FROM Notification_Info_Master WHERE sMesgIDxx=:fsVal")
    ENotificationMaster CheckIfMasterExist(String fsVal);

    @Query("SELECT COUNT(*) FROM Notification_Info_Master")
    int GetNotificationCountForID();
}
