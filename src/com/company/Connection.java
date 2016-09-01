package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Date;


/**
 * Created by Admin on 31.08.2016.
 */
public class Connection extends Thread {
    private Socket socket;
    private int id;
    String msg="";
    InputStream in;
    OutputStream out;

    public Connection(Socket socket){
        this.id=Server.clients;
        this.socket=socket;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {


            BufferedReader rin = new BufferedReader(new InputStreamReader(in));
            BufferedWriter rout= new BufferedWriter(new OutputStreamWriter(out));
            rout.write("You have successfully joined!");
            rout.write("\n");
            rout.flush();
            while(true) {
                msg = rin.readLine();
                if(msg==null)
                    break;
                System.out.println(new Date()+" | User "+this.id+" : "+msg);
                Server.broadcast(new Date()+" | User "+this.id+" : "+msg);

            }
            Server.disconnected();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void send(String str){
        BufferedWriter rout= new BufferedWriter(new OutputStreamWriter(out));
        try {
            rout.write(str);
            rout.write("\n");
            rout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
