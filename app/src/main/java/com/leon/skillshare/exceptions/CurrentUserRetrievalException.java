package com.leon.skillshare.exceptions;


public class CurrentUserRetrievalException extends RuntimeException {
    public CurrentUserRetrievalException() {
        super();
    }

    public CurrentUserRetrievalException(String message) {
        super(message);
    }
}
