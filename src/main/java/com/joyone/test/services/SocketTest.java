package com.joyone.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Order(value=1)
public class SocketTest implements CommandLineRunner {

    @Autowired
    private EmailService emailService;
    @Override
    public void run(String... strings) throws Exception {
//        System.out.println("Scoket Test.................");
//        ServerSocket serversocket= new ServerSocket(8567);
//        while(true){
//            Socket s =serversocket.accept();
//            new AcceptThread(s).start();
//        }
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = format.format(new Date());
        emailService.sendEmail("重启邮件Test_"+time+"_from Heroku","重启邮件Test_"+time);

    }
    class AcceptThread extends Thread{
        private Socket s;
        public AcceptThread(Socket s){
            this.s=s;
        }

        @Override
        public void run() {
            try {
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()) );
                pw.println("connect success");
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
