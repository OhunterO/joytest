package com.joyone.test.services;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Component;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

@Component
public class FtpTest {

    public  FtpClient getFtpClient(){
        FtpClient ftp = null;
        //创建地址
        SocketAddress addr = new InetSocketAddress("121.40.132.9", 21);
        //连接
        ftp = FtpClient.create();
        try {
            ftp.connect(addr);
            //登陆
            ftp.login("lottery", "lottery".toCharArray());
            ftp.setBinaryType();
        } catch (FtpProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftp;
    }

    public  void upload(String localFile, String ftpFile, FtpClient ftp) {
        OutputStream os = null;
        FileInputStream fis = null;
        try {
            // 将ftp文件加入输出流中。输出到ftp上
            os = ftp.putFileStream(ftpFile);
            File file = new File(localFile);

            // 创建一个缓冲区
            fis = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int c;
            while((c = fis.read(bytes)) != -1){
                os.write(bytes, 0, c);
            }
            System.out.println("upload success!!");
        } catch (FtpProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(os!=null) {
                    os.close();
                }
                if(fis!=null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void download(String localFile, String ftpFile, FtpClient ftp) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            // 获取ftp上的文件
            is = ftp.getFileStream(ftpFile);
            File file = new File(localFile);
            byte[] bytes = new byte[1024];
            int i;
            fos = new FileOutputStream(file);
            while((i = is.read(bytes)) != -1){
                fos.write(bytes, 0, i);
            }
            System.out.println("download success!!");

        } catch (FtpProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fos!=null) {
                    fos.close();
                }
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void getPicFromFtp(String ftpFile, HttpServletResponse response,FtpClient ftp) {
        InputStream is = null;
        FileOutputStream fos = null;
        ServletOutputStream outputStream = null;
        try {
            // 获取ftp上的文件
            is = ftp.getFileStream(ftpFile);
            response.setHeader("Content-Type","image/jpeg");
            outputStream = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) > 0) {
                outputStream.write(b, 0, length);
            }
            outputStream.flush();

        } catch (FtpProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fos!=null) {
                    fos.close();
                }
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
