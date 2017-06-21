package com.textanddrive.assesmentdemo.models;

/**
 * Created by Niraj on 22-05-2017.
 */

public class User {
    String id;
    String userName;
    String email;
    String password;
    String confirmPassword;
    String mobileNumber;
    String gender;
    String identity;

    public User() {
    }

    public User(String userName, String email, String password, String confirmPassword, String mobileNumber, String gender, String identity) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.identity = identity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", identity='" + identity + '\'' +
                '}';
    }
}
