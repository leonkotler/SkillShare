package com.leon.skillshare.domain;


public class ServerRequest {
    private boolean succeeded;
    private String message;
    private String refId;

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public ServerRequest(boolean succeeded, String message, String refId) {
        this.succeeded = succeeded;
        this.message = message;
        this.refId = refId;
    }

    public ServerRequest() {
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
