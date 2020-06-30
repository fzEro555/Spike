package com.qx.spike.enums;

/**
 * Created by Qiang Xiao on 06/13/2020
 */
public enum SpikeStatusEnum {
    SUCCESS(1, "Spike succeed"),
    ENDED(0, "Spike ended"),
    REPEAT_SPIKE(-1, "Spike repeated"),
    INNER_ERROR(-2, "System error"),
    DATA_REWRITE(-3, "Data was rewrote");

    private int status;
    private String statusInfo;

    SpikeStatusEnum(int status, String statusInfo){
        this.status = status;
        this.statusInfo = statusInfo;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public static SpikeStatusEnum statusOf(int index){
        for(SpikeStatusEnum status : values()){
            if(status.getStatus() == index){
                return status;
            }
        }
        return null;
    }
}
