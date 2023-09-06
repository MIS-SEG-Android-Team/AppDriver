package org.rmj.g3appdriver.GCircle.Account;

import org.rmj.g3appdriver.lib.accountmanagement.factory.AccountManagement;

public class GCircleAccManagementImpl implements AccountManagement {


    @Override
    public boolean changePassword(String fsOldPassword, String fsNewPassword) {
        return false;
    }

    @Override
    public boolean deactivateAccount() {
        return false;
    }

    @Override
    public boolean reactivateAccount(String fsEmail) {
        return false;
    }
}
