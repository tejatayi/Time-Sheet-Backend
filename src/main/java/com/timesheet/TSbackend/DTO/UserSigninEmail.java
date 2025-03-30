package com.timesheet.TSbackend.DTO;

public class UserSigninEmail {
    private String email;

   public UserSigninEmail() {} // required for deserialization(JSON -> Java Obj)

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
