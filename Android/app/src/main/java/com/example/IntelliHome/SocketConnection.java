package com.example.IntelliHome;

import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class SocketConnection {
    private  Socket socket;
    private PrintWriter out;
    private Scanner in;

    public  void startConnection(){
        try {

            // Cambiar a la dirección IP de su servidor
            socket = new Socket("192.168.0.207", 8000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new Scanner(socket.getInputStream());
            System.out.println("Conectado");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void sendMessage(String message) {
        new Thread(() -> { // un hilo por a parte
            try {
                if (out != null) {
                    out.println(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
