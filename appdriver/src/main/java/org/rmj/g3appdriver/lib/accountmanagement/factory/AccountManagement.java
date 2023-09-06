package org.rmj.g3appdriver.lib.accountmanagement.factory;

public interface AccountManagement {

    boolean changePassword(String fsOldPassword, String fsNewPassword);

    boolean deactivateAccount();

    boolean reactivateAccount(String fsEmail);
}
