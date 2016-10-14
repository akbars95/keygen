package com.mtsmda.keygen.desktop.schedule.demo.jl;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * Created by dminzat on 9/29/2016.
 */
public class DemoJobListener implements JobListener {


    @Override
    public String getName() {
        return "DemoJob";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("jobToBeExecuted _ " + context.getJobDetail().getKey().toString());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("jobExecutionVetoed");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("jobWasExecuted");

        System.out.println("Job: " + context.getJobDetail().getKey().toString());
        if (!jobException.getMessage().equals("")) {
            System.out.println("Exception: " + jobException.getMessage());
        }

    }
}