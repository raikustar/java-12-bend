package com.rainer.veebipood.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CronService {

    // iga sekund, minut, tund, kuupäev, kuu, nädalapäev => default 5 välja
    @Scheduled(cron = "0 * * * * *")
    public void runEveryMinute() {
        Date date = new Date();
        System.out.println(date.getMinutes() + " : " + date.getSeconds());
    }

    @Scheduled(cron = "0 */30 * * * *")
    public void runEvery30Minutes() {
        Date date = new Date();
        System.out.println(date.getMinutes() + " : " + date.getSeconds());
    }

    @Scheduled(cron = "0 0 * * * MON-FRI")
    public void runEveryHourOnWeekDays() {
        Date date = new Date();
        System.out.println(date.getMinutes() + " : " + date.getSeconds());
    }
}
