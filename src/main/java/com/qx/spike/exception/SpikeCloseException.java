package com.qx.spike.exception;

/**
 * Created by Qiang Xiao on 06/12/2020
 * When the spike is over and user still try to execute the spike, the exception will appear
 */
public class SpikeCloseException extends SpikeException{
    public SpikeCloseException(String message){
        super(message);
    }

    public SpikeCloseException(String message, Throwable cause){
        super(message, cause);
    }
}
