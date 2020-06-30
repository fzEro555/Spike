package com.qx.spike.entity;

import java.util.Date;

/**
 * Created by Qiang Xiao on 06/10/2020
 */
public class SuccessfulSpike {
    private long spikeId;
    private long userPhone;
    private short status;
    private Date createTime;

    // n to 1 since we have multiple same products in stock
    private Spike spike;

    public long getSpikeId(){
        return spikeId;
    }

    public void setSpikeId(long spikeId){
        this.spikeId = spikeId;
    }

    public long getUserPhone(){
        return userPhone;
    }

    public void setUserPhone(long userPhone){
        this.userPhone = userPhone;
    }

    public short getStatus(){
        return status;
    }

    public void setStatus(short status){
        this.status = status;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public Spike getSpike(){
        return spike;
    }

    public void setSpike(Spike spike){
        this.spike = spike;
    }

    @Override
    public String toString(){
        return "SuccessfulSpike{" +
                "spikeId = " + spikeId +
                ", userPhone = " + userPhone +
                ", status = " + status +
                ", createTime = " + createTime +
                '}';
    }
}
