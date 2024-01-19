package org.rmj.g3appdriver.lib.authentication.pojo;

public class LoginCredentials {
    private final String Email;
    private final String Password;

    public LoginCredentials(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
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
            return true;
        }
    }
}