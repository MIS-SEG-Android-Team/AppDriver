package org.rmj.g3appdriver.lib.accountmanagement.factory;

import org.rmj.g3appdriver.lib.authentication.pojo.PasswordCredentials;

public interface AccountManagement {

    boolean changePassword(PasswordCredentials foPassword);

    boolean deactivateAccount();

    boolean reactivateAccount(String fsEmail);

    String getMessage();
}
