package com.leon.skillshare.domain;



public class RegisterRequest {

    private String userId;
    private String email;
    private String password;
    private Exception exception;
    private String message;
    private boolean successful;

    public RegisterRequest(String userId, String email, String password, Exception exception, String message, boolean successful) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.exception = exception;
        this.message = message;
        this.successful = successful;
    }

    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public RegisterRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
