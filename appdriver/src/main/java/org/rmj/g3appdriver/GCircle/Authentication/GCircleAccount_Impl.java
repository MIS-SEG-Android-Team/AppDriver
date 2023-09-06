package org.rmj.g3appdriver.GCircle.Authentication;

import android.app.Application;
import android.util.Log;

import org.rmj.g3appdriver.lib.authentication.factory.Auth;
import org.rmj.g3appdriver.lib.authentication.factory.iAuthentication;
import org.rmj.g3appdriver.lib.authentication.factory.iAuthenticate;
import org.rmj.g3appdriver.GCircle.Authentication.obj.ChangePassword_Impl;
import org.rmj.g3appdriver.GCircle.Authentication.obj.EmployeeAuth_Impl;
import org.rmj.g3appdriver.GCircle.Authentication.obj.ForgotPassword_Impl;
import org.rmj.g3appdriver.GCircle.Authentication.obj.Register;
import org.rmj.g3appdriver.GCircle.Authentication.obj.TerminateAccount;

public class GCircleAccount_Impl implements iAuthentication {
    private static final String TAG = GCircleAccount_Impl.class.getSimpleName();

    private final Application instance;

    public GCircleAccount_Impl(Application instance) {
        this.instance = instance;
    }

    @Override
    public iAuthenticate getInstance(Auth params) {
        switch (params){
            case AUTHENTICATE:
                Log.d(TAG, "Initialize employee authentication.");
                return new EmployeeAuth_Impl(instance);
            case CREATE_ACCOUNT:
                Log.d(TAG, "Initialize account registration.");
                return new Register(instance);
            case CHANGE_PASSWORD:
                Log.d(TAG, "Initialize account update.");
                return new ChangePassword_Impl(instance);
            case DEACTIVATE:
                Log.d(TAG, "Initialize account deactivate.");
                return new TerminateAccount(instance);
            default:
                Log.d(TAG, "Initialize forgot password.");
                return new ForgotPassword_Impl(instance);
        }
    }
}
