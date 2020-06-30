package com.qx.spike.dto;

import com.qx.spike.entity.SuccessfulSpike;
import com.qx.spike.enums.SpikeStatusEnum;

/**
 * Created by Qiang Xiao on 06/12/2020
 */
public class SpikeExecution {
    private long spikeId;

    // The result status of spike
    private int status;

    // The meaning of status
    private String statusInfo;

    // When the spike is successful, we need to pass the successful spike back
    private SuccessfulSpike successfulSpike;

    public SpikeExecution(long spikeId, SpikeStatusEnum spikeStatusEnum, SuccessfulSpike successfulSpike){
        this.spikeId = spikeId;
        this.status = spikeStatusEnum.getStatus();
        this.statusInfo = spikeStatusEnum.getStatusInfo();
        this.successfulSpike = successfulSpike;
    }

    public  SpikeExecution(long spikeId, SpikeStatusEnum spikeStatusEnum){
        this.spikeId = spikeId;
        this.status = spikeStatusEnum.getStatus();
        this.statusInfo = spikeStatusEnum.getStatusInfo();
    }

    public long getSpikeId(){
        return spikeId;
    }

    public void setSpikeId(long spikeId){
        this.spikeId = spikeId;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public String getStatusInfo(){
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo){
        this.statusInfo = statusInfo;
    }

    public SuccessfulSpike getSuccessfulSpike() {
        return successfulSpike;
    }

    public void setSuccessfulSpike(SuccessfulSpike successfulSpike) {
        this.successfulSpike = successfulSpike;
    }

    @Override
    public String toString(){
        return "SpikeExecution{" +
                "spikeId = " + spikeId +
                ", status = " + status +
                ", statusInfo = '" + statusInfo + '\'' +
                ", successfulSpike = " + successfulSpike +
                '}';
    }
}
