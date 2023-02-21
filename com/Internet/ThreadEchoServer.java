package com_second.Internet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ThreadEchoServer {
    public static void main(String[] args) {
        try (var  s=  new ServerSocket(8189)){
            int i = 1;
            while (true){
                Socket accept = s.accept();
                System.out.println("Spawning " + i);
                ThreadedEchoHandler handler = new ThreadedEchoHandler(accept);
                Thread thread = new Thread(handler);
                thread.start();
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class ThreadedEchoHandler implements Runnable{
    private Socket incoming;

    public ThreadedEchoHandler(Socket incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {
        try (InputStream inStream =  incoming.getInputStream();
             OutputStream outStream = incoming.getOutputStream();
             var in = new Scanner(inStream, StandardCharsets.UTF_8);
             var out = new PrintWriter(new OutputStreamWriter(outStream,StandardCharsets.UTF_8),true)) {
            out.println("Hello! Enter BYE to exit!");

            var done = false;
            while (!done && in.hasNextLine()){
                String s1 = in.nextLine();
                out.println("Echo: " + s1);
                if (s1.trim().equals("BYE")) done = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
