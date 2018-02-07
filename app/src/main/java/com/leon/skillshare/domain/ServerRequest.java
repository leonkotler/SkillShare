package com.leon.skillshare.domain;


public class ServerRequest {
    private boolean successful;
    private String message;
    private String refId;

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public ServerRequest(boolean successful, String message, String refId) {
        this.successful = successful;
        this.message = message;
        this.refId = refId;
    }

    public ServerRequest() {
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
