package com.joyone.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Configurable
@EnableScheduling
public class JobService {

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 */40 * * * ? ")
    public void sendEmail(){
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = format.format(new Date());
        emailService.sendEmail("JobEmail_"+time+"_from Heroku","JobEmail_"+time);
    }


}
