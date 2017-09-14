package com.joyone.test.services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Component
@Order(value=1)
public class SocketTest implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Scoket Test.................");
        ServerSocket serversocket= new ServerSocket(8567);
        while(true){
            Socket s =serversocket.accept();
            new AcceptThread(s).start();
        }

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
