package com.qx.spike.dao;

import com.qx.spike.entity.SuccessfulSpike;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Qiang Xiao on 06/10/2020
 */
public interface SuccessfulSpikeDao {
    /**
     * Insert spike purchase details, can filter duplicates
     * @param spikeId
     * @param userPhone
     * @return
     */
    int insertSuccessfulSpike(@Param("spikeId") long spikeId, @Param("userPhone") long userPhone);

    /**
     * Query successful spike by spike's id
     * @param spikeId
     * @param userPhone
     * @return
     */
    SuccessfulSpike queryByIdWithSpike(@Param("spikeId") long spikeId, @Param("userPhone") long userPhone);
}
