package com.mtsmda.keygen.desktop.schedule.demo.jl;

import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

/**
 * Created by dminzat on 9/29/2016.
 */
public class CronTriggerDemoJob {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        JobKey jobKey = new JobKey("jobName, group");
        JobDetail jobDetail = JobBuilder.newJob(DemoJob.class).withIdentity(jobKey).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("triggerName", "group")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        scheduler.getListenerManager().addJobListener(new DemoJobListener(),
                KeyMatcher.keyEquals(jobKey));
        System.out.println("before = " + scheduler.getJobDetail(jobDetail.getKey()));
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("after = " + scheduler.getJobDetail(jobDetail.getKey()));
        Thread.sleep(5000);
        Trigger triggerNew = TriggerBuilder.newTrigger().withIdentity("triggerName", "group")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/25 * * * * ?")).build();

        scheduler.rescheduleJob(trigger.getKey(), triggerNew);

    }

}