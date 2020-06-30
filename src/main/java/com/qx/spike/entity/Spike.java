package com.qx.spike.entity;

import java.util.Date;

/**
 * Created by Qiang Xiao on 06/10/2020
 */
public class Spike {
    private long spikeId;
    private String name;
    private int quantity;
    private Date startTime;
    private Date endTime;
    private Date createTime;

    public long getSpikeId(){
        return spikeId;
    }

    public void setSpikeId(long spikeId){
        this.spikeId = spikeId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public Date getStartTime(){
        return startTime;
    }

    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }

    public Date getEndTime(){
        return endTime;
    }

    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    @Override
    public String toString(){
        return "Spike{" +
                "spikeId = " + spikeId +
                ", name = '" + name + '\'' +
                ", quantity = " + quantity +
                ", startTime = " + startTime +
                ", endTime = " + endTime +
                ", createTime = " + createTime +
                '}';
    }
}