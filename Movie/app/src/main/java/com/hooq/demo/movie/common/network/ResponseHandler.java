package com.hooq.demo.movie.common.network;

public interface ResponseHandler<T> {

    void onReceive(Response<T> response);
}
