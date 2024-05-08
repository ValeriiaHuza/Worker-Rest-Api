package com.springbootcourse.worker_api.error;

public class WorkerNotFoundException extends RuntimeException  {
    public WorkerNotFoundException(String message) {
        super(message);
    }
}
