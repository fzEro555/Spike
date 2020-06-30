package com.qx.spike.dao;

import com.qx.spike.entity.SuccessfulSpike;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Qiang Xiao on 06/12/2020
 * Configure spring and junit integration so that junit will load the spring container when it starts
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessfulSpikeDaoTest {

    @Autowired
    private SuccessfulSpikeDao successfulSpikeDao;

    @Test
    public void insertSuccessfulSpike() {
        long id = 1000;
        long userPhone = 15600608077L;
        int insertSuccessfulSpike = successfulSpikeDao.insertSuccessfulSpike(id, userPhone);
        System.out.println("insertSuccessfulSpikeCount: " + insertSuccessfulSpike);
    }

    //Todo: successfulSpike.getSpike() returns null
    @Test
    public void queryByIdWithSpike() {
        long id = 1000;
        long userPhone = 15600608077L;
        SuccessfulSpike successfulSpike = successfulSpikeDao.queryByIdWithSpike(id, userPhone);
        System.out.println(successfulSpike);
        System.out.println(successfulSpike.getSpike());
    }
}