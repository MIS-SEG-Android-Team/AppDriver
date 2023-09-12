package org.rmj.g3appdriver.lib.accountmanagement;

import android.app.Application;

import org.rmj.g3appdriver.Config.AppConfig;
import org.rmj.g3appdriver.GCircle.Account.GCircleAccManagementImpl;
import org.rmj.g3appdriver.GConnect.Account.GConnectAccManagementImpl;
import org.rmj.g3appdriver.lib.accountmanagement.factory.AccountManagement;

public class AccountFactoryProvider {
    private static final String TAG = "AccountFactoryProvider";

    private final Application instance;

    private static AccountFactoryProvider poInstance;

    private AccountFactoryProvider(Application instance) {
        this.instance = instance;
    }

    public static AccountFactoryProvider getInstance(Application instance) {
        if(poInstance == null){
            poInstance = new AccountFactoryProvider(instance);
        }

        return poInstance;
    }

    public AccountManagement getAccountManager(){
        String lsProdctID = AppConfig.getInstance(instance).getProductID();
        switch (lsProdctID){
            case "gRider":
                return new GCircleAccManagementImpl(instance);

            case "GuanzonApp":
                return new GConnectAccManagementImpl(instance);

            default:
                throw new IllegalArgumentException("Product ID is not properly initialize.");
        }
    }
}
