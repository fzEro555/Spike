package com.qx.spike.exception;

/**
 * Created by Qiang Xiao on 06/12/2020
 * All exceptions related to spike service
 */
public class SpikeException extends RuntimeException{
    public SpikeException(String message){
        super(message);
    }

    public SpikeException(String message, Throwable cause){
        super(message, cause);
    }
}
