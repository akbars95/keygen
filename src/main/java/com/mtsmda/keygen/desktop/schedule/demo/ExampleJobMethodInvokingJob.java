package com.mtsmda.keygen.desktop.schedule.demo;

import org.springframework.stereotype.Component;

/**
 * Created by dminzat on 9/22/2016.
 */
@Component("exampleJobMethodInvokingJob")
public class ExampleJobMethodInvokingJob {

    private static int count = 0;

    public void doIt(){
        System.out.println(++count);
    }

}