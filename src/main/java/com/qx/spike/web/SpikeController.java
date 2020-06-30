package com.qx.spike.web;

import com.qx.spike.dto.SpikeResult;
import com.qx.spike.dto.Exposer;
import com.qx.spike.dto.SpikeExecution;
import com.qx.spike.enums.SpikeStatusEnum;
import com.qx.spike.exception.RepeatSpikeException;
import com.qx.spike.exception.SpikeCloseException;
import com.qx.spike.service.SpikeService;
import com.qx.spike.entity.Spike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Date;
/**
 * Created by Qiang Xiao on 06/14/2020
 */
@Component
@RequestMapping("/spike")
public class SpikeController {
    @Autowired
    private SpikeService spikeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        List<Spike> list = spikeService.getSpikeList();
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping(value = "/{spikeId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("spikeId") Long spikeId, Model model){
        if(spikeId == null){
            return "redirect:/spike/list";
        }

        Spike spike = spikeService.getSpikeById(spikeId);
        if(spike == null){
            return "forward:/spike/list";
        }

        model.addAttribute("spike", spike);
        return "detail";
    }

    @RequestMapping(value = "/{spikeId}/exposer",
                    method = RequestMethod.GET,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SpikeResult<Exposer> exposer(@PathVariable("spikeId") Long spikeId){
        SpikeResult<Exposer> result;

        try{
            Exposer exposer = spikeService.exportSpikeUrl(spikeId);
            result = new SpikeResult<Exposer>(true, exposer);
        }catch(Exception e){
            e.printStackTrace();
            result = new SpikeResult<Exposer>(false, e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/{spikeId}/{md5}/execution",
                    method = RequestMethod.POST,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SpikeResult<SpikeExecution> execute(@PathVariable("spikeId") Long spikeId,
                                               @PathVariable("md5") String md5,
                                               @CookieValue(value = "userPhone", required = false) Long userPhone){
        SpikeResult<SpikeExecution> result;
        if(userPhone == null){
            return new SpikeResult<SpikeExecution>(false, "Not registered");
        }

        try{
            SpikeExecution spikeExecution = spikeService.executeSpike(spikeId, userPhone, md5);
            result = new SpikeResult<SpikeExecution>(true, spikeExecution);
        }catch(RepeatSpikeException e1){
            SpikeExecution spikeExecution = new SpikeExecution(spikeId, SpikeStatusEnum.REPEAT_SPIKE);
            result = new SpikeResult<SpikeExecution>(false, spikeExecution);
        }catch(SpikeCloseException e2){
            SpikeExecution spikeExecution = new SpikeExecution(spikeId, SpikeStatusEnum.ENDED);
            result = new SpikeResult<SpikeExecution>(false, spikeExecution);
        }catch(Exception e){
            SpikeExecution spikeExecution = new SpikeExecution(spikeId, SpikeStatusEnum.INNER_ERROR);
            result = new SpikeResult<SpikeExecution>(false, spikeExecution);
        }

        return result;
    }

    // Get system time
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SpikeResult<Long> time(){
        Date currentTime = new Date();
        return new SpikeResult<Long>(true, currentTime.getTime());
    }
}
