package com.leon.skillshare.domain;

import android.net.Uri;

/**
 * Created by leonko on 06-Feb-18.
 */

public class ResourceUploadRequest {
    private boolean succeeded;
    private Uri downloadUri;
    private String message;

    public ResourceUploadRequest(boolean succeeded, Uri downloadUri, String message) {
        this.succeeded = succeeded;
        this.downloadUri = downloadUri;
        this.message = message;
    }

    public ResourceUploadRequest() {
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public Uri getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(Uri downloadUri) {
        this.downloadUri = downloadUri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
