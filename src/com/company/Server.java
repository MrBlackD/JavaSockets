package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static int port= 7777;

    public static int clients=0;
    public final static int max_clients=2;
    private static ArrayList<Connection> connections;

    public static void main(String[] args) {
	// write your code here
        try {
            ServerSocket server=new ServerSocket(port);
            System.out.println("Waiting for a client...");

            connections=new ArrayList<>();
            ExecutorService es = Executors.newFixedThreadPool(max_clients);

            Connection tmp;
            while(true) {


                Socket socket = server.accept();
                if(max_clients<=clients){
                    OutputStream out = socket.getOutputStream();
                    BufferedWriter rout= new BufferedWriter(new OutputStreamWriter(out));
                    rout.write("Chat is full("+max_clients+" users). You can't join. Try again later.");
                    rout.write("\n");
                    rout.flush();
                    socket.close();
                    out.close();
                    continue;
                }

                tmp=new Connection(socket);
                es.submit(tmp);
                clients++;
                System.out.println("New user has joined. Number of users - "+clients);
                connections.add(tmp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void disconnected(){
        clients--;
        System.out.println("User has disconnected. Number of users - "+clients);
    }

    public static void broadcast(String str){
        for (Connection connection: connections) {
            connection.send(str);
        }
    }
}
