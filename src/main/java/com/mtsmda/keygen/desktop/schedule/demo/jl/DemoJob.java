package com.mtsmda.keygen.desktop.schedule.demo.jl;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/**
 * Created by dminzat on 9/29/2016.
 */
public class DemoJob extends QuartzJobBean{

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Time Now is - " + LocalDateTime.now());
    }
}