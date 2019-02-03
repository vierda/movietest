package com.hooq.demo.movie.common.util;


import rx.Scheduler;

public interface PostExecutionThread {
    Scheduler getScheduler();
}
