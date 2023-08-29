package org.rmj.g3appdriver.GCircle.Apps.PetManager.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Account.EmployeeSession;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Http.HttpHeaderManager;
import org.rmj.g3appdriver.dev.Http.HttpHeaderProvider;

public abstract class PetMngr {
    protected final GCircleApi poApi;
    protected final HttpHeaderProvider poHeaders;
    protected final EmployeeSession poSession;
    protected final EmployeeMaster poUser;
    protected String message;

    public PetMngr(Application instance) {
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaderManager.getInstance(instance).initializeHeader();
        this.poSession = EmployeeSession.getInstance(instance);
        this.poUser = new EmployeeMaster(instance);
    }

    public abstract LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo();

    public abstract boolean ImportApplications();

    public abstract boolean DownloadApplication(String args);

    public abstract String SaveApplication(Object foVal);

    public abstract boolean UploadApplication(String args);

    public abstract String SaveApproval(Object foVal);

    public abstract boolean UploadApproval(Object args);

    public abstract boolean UploadApplications();

    public abstract String getMessage();

}
