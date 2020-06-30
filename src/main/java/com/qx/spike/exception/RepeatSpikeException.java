package com.qx.spike.exception;

/**
 * Created by Qiang Xiao on 06/12/2020
 * Runtime Exception, we don't have to manually try and catch
 * Mysql only supports rollback operations during runtime exceptions
 */
public class RepeatSpikeException extends SpikeException{
    public RepeatSpikeException(String message){
        super(message);
    }

    public RepeatSpikeException(String message, Throwable cause){
        super(message, cause);
    }
}
