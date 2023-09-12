package org.rmj.g3appdriver.lib.Notifications.obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.lib.Notifications.data.dao.DNotification;
import org.rmj.g3appdriver.lib.Notifications.data.entity.ENotificationMaster;
import org.rmj.g3appdriver.lib.Notifications.data.entity.ENotificationRecipient;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Notifications.NOTIFICATION_STATUS;
import org.rmj.g3appdriver.lib.Notifications.obj.receiver.NMM_Regular;

import java.util.List;

public class Notification extends NMM_Regular {
    private static final String TAG = Notification.class.getSimpleName();

    private final DNotification poDao;

    public Notification(Application instance) {
        super(instance);
        this.poDao = GGC_GCircleDB.getInstance(instance).notificationDao();
    }

    public LiveData<List<DNotification.NotificationListDetail>> GetOtherNotificationList(){
        return poDao.GetNotificationList();
    }

    public LiveData<Integer> GetUnreadNotificationCount(){
        return poDao.GetUnreadNotificationCount();
    }

    public LiveData<ENotificationMaster> GetNotificationMaster(String args){
        return poDao.GetNotificationMaster(args);
    }

    public LiveData<ENotificationRecipient> GetNotificationDetail(String args){
        return poDao.GetNotificationDetail(args);
    }

    public LiveData<Integer> GetAllUnreadNotificationCount(){
        return poDao.GetAllUnreadNotificationCount();
    }

    @Override
    public ENotificationMaster SendResponse(String mesgID, NOTIFICATION_STATUS status) {
        return super.SendResponse(mesgID, status);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
