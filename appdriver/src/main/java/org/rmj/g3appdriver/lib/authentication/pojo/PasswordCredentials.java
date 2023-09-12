package org.rmj.g3appdriver.lib.authentication.pojo;

public class PasswordCredentials {
    private final String sOldPwrdx;
    private final String sNewPwrdx;
    private final String sPwrdCfrm;

    public PasswordCredentials(String OldPassword, String NewPassword, String ConfirmPassword) {
        this.sOldPwrdx = OldPassword;
        this.sNewPwrdx = NewPassword;
        this.sPwrdCfrm = ConfirmPassword;
    }

    public String getOldPassword() {
        return sOldPwrdx;
    }

    public String getNewPassword() {
        return sNewPwrdx;
    }

    public String getConfirmPassword() {
        return sPwrdCfrm;
    }

    public static class PasswordValidator{

        private String message;

        public PasswordValidator() {

        }

        public String getMessage() {
            return message;
        }

        public boolean isDataValid(PasswordCredentials foVal){
            if(foVal.getOldPassword().isEmpty()){
                message = "Please enter old password";
                return false;
            }
            if(foVal.getNewPassword().isEmpty()){
                message = "Please enter new password";
                return false;
            }
            if(foVal.getConfirmPassword().isEmpty()){
                message = "Please confirm new password";
                return false;
            }
            if(!foVal.getNewPassword().equalsIgnoreCase(
                    foVal.getConfirmPassword())){
                message = "New password does not match";
                return false;
            }
            if(foVal.getNewPassword().length() < 8){
                message = "Please enter atleast 8 characters password";
                return false;
            }
            if(foVal.getNewPassword().length() > 15){
                message = "Password is too long. Must be less than 15 characters";
                return false;
            }
            return true;
        }
    }
}
