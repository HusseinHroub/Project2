package com.example.project2.background;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MtTestSenderThread extends Thread {

    private String message = "gRes";
    private DatagramPacket datagramPacket;
    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private static final int PORT = 8888;
    private boolean runnable = true;

    public MtTestSenderThread(){

        start();
    }

    @Override
    public void run() {
        try {
            inetAddress = InetAddress.getByName("192.168.1.177");
            datagramSocket = new DatagramSocket();
            byte[] messageArray = message.getBytes();
            datagramPacket = new DatagramPacket(messageArray, messageArray.length, inetAddress, PORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while(runnable) {
            try {
                datagramSocket.send(datagramPacket);
                System.out.println("sent packet!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    public void stopThread()
    {

        runnable = false;
        datagramSocket.close();
    }
}
