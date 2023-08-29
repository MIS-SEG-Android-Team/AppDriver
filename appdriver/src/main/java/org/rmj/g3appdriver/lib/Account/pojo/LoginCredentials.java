package org.rmj.g3appdriver.lib.Account.pojo;

public class LoginCredentials {
    private final String Email;
    private final String Password;
    private final String MobileNo;

    public LoginCredentials(String email, String password, String mobileNo) {
        Email = email;
        Password = password;
        MobileNo = mobileNo;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public static class EntryValidator{

        private String message;

        public EntryValidator() {

        }

        public String getMessage(){
            return message;
        }

        public boolean isDataValid(LoginCredentials foCredentials){

            if(foCredentials.getEmail() == null) {
                message = "Please enter your email";
                return false;
            }

            if(foCredentials.getEmail().isEmpty()){
                message = "Please enter your email";
                return false;
            }

            if(foCredentials.getPassword() == null){
                message = "Please enter password";
                return false;
            }

            if(foCredentials.getPassword().isEmpty()){
                message = "Please enter password";
                return false;
            }

            if(foCredentials.getMobileNo() == null){
                message = "Please enter mobile no.";
                return false;
            }

            if(foCredentials.getMobileNo().isEmpty()){
                message = "Please enter mobile no.";
                return false;
            }

            if(foCredentials.getMobileNo().length()!=11){
                message = "Mobile number must be 11 characters";
                return false;
            }

            if(!foCredentials.getMobileNo().substring(0, 2)
                    .equalsIgnoreCase("09")){
                message = "Mobile number must start with '09'";
                return false;
            }

            return true;
        }
    }
}