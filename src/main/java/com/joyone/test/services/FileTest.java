package com.joyone.test.services;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FileTest {

    public void createFile(){
        String fileName = "one.txt";
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTxt(){
        String fileName = "one.txt";
        File file = new File(fileName);
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            fos = new FileOutputStream(file);
            pw  = new PrintWriter(fos);
            pw.write("rtyuiuioio".toCharArray());
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                pw.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String readTxt(){
        String fileName = "one.txt";
        File file = new File(fileName);
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String temp = "";
        StringBuffer buf = new StringBuffer();

        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                buf = buf.append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
                isr.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buf.toString();
    }

}
