package com.leon.skillshare.domain;


public class LoginRequest {

    private boolean successful;
    private String email;
    private String password;
    private String message;


    public LoginRequest(boolean successful, String email, String password, String message) {
        this.successful = successful;
        this.email = email;
        this.password = password;
        this.message = message;
    }

    public LoginRequest(boolean successful, String email, String password) {
        this.successful = successful;
        this.email = email;
        this.password = password;
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequest() {
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
