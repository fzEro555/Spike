package com.qx.spike.dao;

import com.qx.spike.entity.Spike;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Qiang Xiao on 06/10/2020
 */
public interface SpikeDao {

    /**
     * reduce the quantity of products in stock
     * @param spikeId
     * @param spikeTime
     * @return If the infected row number > 1, it represents the number of record lines for updating inventory
     */
    int reduceQuantity(@Param("spikeId") long spikeId, @Param("spikeTime") Date spikeTime);

    /**
     * Query the spiked product information by id
     * @param spikeId
     * @return
     */
    Spike queryById(@Param("spikeId") long spikeId);

    /**
     * Query the list of spike products according to the offset
     * @param off
     * @param limit
     * @return
     */
    List<Spike> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
