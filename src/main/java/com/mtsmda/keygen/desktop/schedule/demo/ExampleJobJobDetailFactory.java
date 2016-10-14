package com.mtsmda.keygen.desktop.schedule.demo;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/**
 * Created by dminzat on 9/22/2016.
 */
public class ExampleJobJobDetailFactory extends QuartzJobBean{

    private int timeout;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("ExampleJobJobDetailFactory - " + LocalDateTime.now());
    }
}