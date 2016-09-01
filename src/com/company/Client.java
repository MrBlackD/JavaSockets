package com.company;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.RuleBasedCollator;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Admin on 31.08.2016.
 */
public class Client {
    public final static int serverPort=7777;
    public final static String address="localhost";

    public static void main(String[] args) {
        InetAddress ipAddress = null;

        try {
            ipAddress = InetAddress.getByName(address);
            Socket socket = new Socket(ipAddress, serverPort);

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            BufferedReader rin = new BufferedReader(new InputStreamReader(in));
            BufferedWriter rout= new BufferedWriter(new OutputStreamWriter(out));



            new Thread(new Runnable() {
                @Override
                public void run() {
                    String msg="";
                    while(true){
                        try {
                            msg = rin.readLine();
                            if(msg==null){
                                System.out.println("Connection lost");
                                break;
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(msg);
                    }
                }
            }).start();


            rout.flush();
            BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
            String msg="";
            while(true){
                msg=keyboardInput.readLine();
                rout.write(msg);
                rout.write("\n");
                rout.flush();

            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
