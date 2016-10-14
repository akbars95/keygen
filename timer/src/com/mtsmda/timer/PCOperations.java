package com.mtsmda.timer;

import java.io.IOException;
import java.time.*;

/**
 * Created by dminzat on 9/12/2016.
 */
public class PCOperations {
    private static final String SHUTDOWN_TEXT = "shutdown";

    public static final String LOG_OFF = "/l";
    public static final String SHUTDOWN = "/s";
    public static final String RESTART = "/r";
    public static final String HIBERNATE = "/h";
    public static final String FORSE = "/f";
    public static final String TIMER = "/t";
    private static Runtime rt = Runtime.getRuntime();

    public static String[] operations = {"", "Log off", "Shutdown", "Restart", "Hibernate"};

    public void cancel() throws Exception {
        Process proc = rt.exec(SHUTDOWN_TEXT + " /a");
    }

    /*
    *  shutdown [-i | -l | -s | -r | -a] [-f] [-m \\computername] [-t xx] [-c "comment"] [-d up:xx:yy]
    * */
    public void command(Integer type, boolean isSelected, long seconds) throws IOException {
        StringBuilder command = new StringBuilder(SHUTDOWN_TEXT + " ");
        switch (type) {
            case 1: {
                command.append(LOG_OFF);
            }
            break;
            case 2: {
                command.append(SHUTDOWN);
            }
            break;
            case 3: {
                command.append(RESTART);
            }
            break;
            case 4: {
                command.append(HIBERNATE);
            }
            break;
            default: {
                throw new RuntimeException("Nothing not chose!");
            }
        }
        command.append(" ");
        if (isSelected) {
            command.append(FORSE).append(" ");
        }
        command.append(TIMER).append(" ").append(seconds);
        System.out.println(command);
//        Process proc = rt.exec(command.toString());
    }

//    public static void main(String[] args) throws Exception {
        /*new PCOperations().cancel();
        System.out.println("cancel!");*/
        /*LocalDate localDateNow = LocalDate.now();
        LocalDate localDateTomorrow = localDateNow.plusDays(35);
        System.out.println(localDateNow);
        System.out.println(localDateTomorrow);
        LocalDate minus = localDateTomorrow.minus(Period.of(localDateNow.getYear(), localDateNow.getMonthValue(), localDateNow.getDayOfMonth()));
        System.out.println(minus);

        System.out.println(localDateNow.toEpochDay());
        LocalDate localDateYesterday = localDateNow.minusDays(1);
        System.out.println(localDateNow.isAfter(localDateYesterday));


        LocalDate localDate = LocalDate.now();
        LocalDate localDateMore10days = localDate.plusDays(10);*/
//        Period between = Period.between(localDate, localDateMore10days);
//        System.out.println(between);
//        Duration between1 = Duration.between(localDate, localDateMore10days);
//        System.out.println(between1);

//        LocalDateTime localDateTime = LocalDateTime.now();
//        LocalDateTime localDateTime1Year = localDateTime.plusYears(1);

       /* System.out.println("\n\n\n\n\n______________________________________");
        localDateTimePeriodAndDuration();
    }*/

    private static void localDateTimePeriodAndDuration() {
        LocalTime localTime = LocalTime.now();
        LocalTime localTimePlus1HourAnd25Minute = localTime.plusHours(1).plusMinutes(25);
        System.out.println(localTime);
        System.out.println(localTimePlus1HourAnd25Minute);
        Duration between = Duration.between(localTime, localTimePlus1HourAnd25Minute);
        System.out.println(between);
        System.out.println(between.getSeconds());
        System.out.println(between.getUnits());

        System.out.println("                  **************************                     ");

        LocalDate localDate = LocalDate.now();
        LocalDate localDate1Day2Months1Year = localDate.plusYears(1).plusMonths(2).plusDays(1);
        System.out.println(localDate);
        System.out.println(localDate1Day2Months1Year);
        Period between1 = Period.between(localDate, localDate1Day2Months1Year);
        System.out.println(between1);
        System.out.println(between1.getUnits());
        System.out.println("year = " + between1.getYears());
        System.out.println("month = " + between1.getMonths());
        System.out.println("day = " + between1.getDays());
        System.out.println("                  **************************                     ");

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime1Y2M3D4H5Min9S = localDateTime.plusYears(1).plusMonths(2).plusDays(3)
                                                    .plusHours(4).plusMinutes(5).plusSeconds(9);
        System.out.println(localDateTime);
        System.out.println(localDateTime1Y2M3D4H5Min9S);
        Duration between2 = Duration.between(localDateTime, localDateTime1Y2M3D4H5Min9S);
        System.out.println(between2.getUnits());
        System.out.println(between2.getSeconds());

        System.out.println(Duration.between(LocalDateTime.now(), LocalDateTime.now().plusHours(1).plusMinutes(1)).getSeconds());
    }

}