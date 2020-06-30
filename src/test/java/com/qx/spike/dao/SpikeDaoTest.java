package com.qx.spike.dao;

import com.qx.spike.entity.Spike;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by Qiang Xiao on 06/11/2020
 * Configure spring and junit integration so that junit will load the spring container when it starts
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SpikeDaoTest {

    @Autowired
    private SpikeDao spikeDao;

    @Test
    public void reduceQuantity() {
        long id = 1000;
        Date date = new Date();
        int updatedQuantity = spikeDao.reduceQuantity(id, date);
        Spike spike = spikeDao.queryById(id);
        System.out.println(updatedQuantity);
        System.out.println(spike.getQuantity());
    }

    @Test
    public void queryById() throws Exception {
        long id = 1000;
        Spike spike = spikeDao.queryById(id);
        System.out.println(spike.getName());
        System.out.println(spike);
    }

    @Test
    public void queryAll() {
        List<Spike> spikes = spikeDao.queryAll(0, 100);
        for (Spike spike : spikes){
            System.out.println(spike);
        }

    }
}