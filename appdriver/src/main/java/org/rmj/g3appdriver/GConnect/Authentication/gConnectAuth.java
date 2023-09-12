package org.rmj.g3appdriver.GConnect.Authentication;

import android.app.Application;
import android.util.Log;

import org.rmj.g3appdriver.lib.authentication.factory.Auth;
import org.rmj.g3appdriver.lib.authentication.factory.iAuthentication;
import org.rmj.g3appdriver.lib.authentication.factory.iAuthenticate;
import org.rmj.g3appdriver.GConnect.Authentication.obj.ResetPassword;
import org.rmj.g3appdriver.GConnect.Authentication.obj.SignIn;
import org.rmj.g3appdriver.GConnect.Authentication.obj.SignUp;
import org.rmj.g3appdriver.GConnect.Authentication.obj.UpdatePassword;

public class gConnectAuth implements iAuthentication {
    private static final String TAG = gConnectAuth.class.getSimpleName();

    private final Application instance;

    public gConnectAuth(Application instance) {
        this.instance = instance;
    }

    @Override
    public iAuthenticate getInstance(Auth params) {
        switch (params){
            case AUTHENTICATE:
                Log.d(TAG, "Initialize client sign in.");
                return new SignIn(instance);
            case CREATE_ACCOUNT:
                Log.d(TAG, "Initialize client sign up.");
                return new SignUp(instance);
            case CHANGE_PASSWORD:
                Log.d(TAG, "Initialize update password.");
                return new UpdatePassword(instance);
            default:
                Log.d(TAG, "Initialize reset password.");
                return new ResetPassword(instance);
        }
    }
}
