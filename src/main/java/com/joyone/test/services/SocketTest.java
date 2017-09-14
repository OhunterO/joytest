package com.joyone.test.services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;

@Component
@Order(value=1)
public class SocketTest implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Scoket Test.................");
        ServerSocket ss= new ServerSocket(8567);
    }
}
