package com.qx.spike.service;

import com.qx.spike.dto.SpikeExecution;
import com.qx.spike.exception.RepeatSpikeException;
import com.qx.spike.exception.SpikeCloseException;
import com.qx.spike.exception.SpikeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

import com.qx.spike.entity.Spike;
import com.qx.spike.dto.Exposer;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                        "classpath:spring/spring-service.xml"})
public class SpikeServiceTest {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SpikeService spikeService;

    @Test
    public void getSpikeList() throws Exception{
        List<Spike> spikes = spikeService.getSpikeList();
        for(Spike spike: spikes){
            System.out.println(spike);
        }
    }

    @Test
    public void getSpikeById() {
        long id = 1000;
        Spike spike = spikeService.getSpikeById(id);
        System.out.println(spike);
    }

    @Test
    public void exportSpikeUrl() {
        long id = 1000;
        Exposer exposer = spikeService.exportSpikeUrl(id);
        System.out.print(exposer);
    }

    @Test
    public void executeSpike() {
        long id = 1000;
        long userPhone = 15600608077L;
        String md5 = "b32e48ff449e9f6c350fc72e29ed8cbd";
        try{
            SpikeExecution spikeExecution = spikeService.executeSpike(id, userPhone, md5);
            System.out.println(spikeExecution);
        }catch(RepeatSpikeException e1){
            e1.printStackTrace();
        }catch(SpikeCloseException e2){
            e2.printStackTrace();
        }
    }

    @Test
    public void testSpikeLogic(){
        long id = 1000;
        Exposer exposer = spikeService.exportSpikeUrl(id);
        if(exposer.isExposed()){
            System.out.println(exposer);

            long userPhone = 13120208888L;
            String md5 = exposer.getMd5();
            try{
                SpikeExecution spikeExecution = spikeService.executeSpike(id, userPhone, md5);
                System.out.println(spikeExecution);
            }catch(RepeatSpikeException e1){
                e1.printStackTrace();
            }catch(SpikeCloseException e2){
                e2.printStackTrace();
            }
        }
        else{
            System.out.println(exposer);
        }
    }
}