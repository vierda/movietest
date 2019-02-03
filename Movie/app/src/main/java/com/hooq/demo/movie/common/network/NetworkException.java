package com.hooq.demo.movie.common.network;

public class NetworkException extends Exception {

    public static final int BAD_REQUEST = 404;
    public static final int SERVER_ERROR = 500;

    private int errorCode = -1;

    public NetworkException(int errorCode, String message) {
        super(message);

        this.errorCode = errorCode;
    }

    public NetworkException(int errorCode) {

        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
