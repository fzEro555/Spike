package com.qx.spike.dto;

import java.util.Date;

/**
 * Created by Qiang Xiao on 06/12/2020
 * Expose spike url(interface) DTO
 */
public class Exposer {

    // Whether to enable spike
    private boolean exposed;

    // Encryption
    private String md5;

    private long spikeId;

    // Current system time(millisecond)
    private long currentTime;

    // Spike start time
    private long startTime;

    // Spike end time
    private long endTime;

    public Exposer(boolean exposed, String md5, long spikeId){
        this.exposed = exposed;
        this.md5 = md5;
        this.spikeId = spikeId;
    }

    public Exposer(boolean exposed, long spikeId, long currentTime, long startTime, long endTime){
        this.exposed = exposed;
        this.spikeId = spikeId;
        this.currentTime = currentTime;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Exposer(boolean exposed, long spikeId){
        this.exposed = exposed;
        this.spikeId = spikeId;
    }

    public boolean isExposed(){
        return exposed;
    }

    public void setExposed(boolean exposed){
        this.exposed = exposed;
    }

    public String getMd5(){
        return md5;
    }

    public void setMd5(String md5){
        this.md5 = md5;
    }

    public long getCurrentTime(){
        return currentTime;
    }

    public void setCurrentTime(long currentTime){
        this.currentTime = currentTime;
    }

    public long getStartTime(){
        return startTime;
    }

    public void setStartTime(long startTime){
        this.startTime = startTime;
    }

    public long getEndTime(){
        return endTime;
    }

    public void setEndTime(long endTime){
        this.endTime = endTime;
    }

    @Override
    public String toString(){
        return "Exposer{" +
                "exposed = " + exposed +
                ", md5 = '" + md5 + '\'' +
                ", spikeId = " + spikeId +
                ", currentTime = " + currentTime +
                ", startTime = " + startTime +
                ", endTime = " + endTime +
                '}';
    }
}
