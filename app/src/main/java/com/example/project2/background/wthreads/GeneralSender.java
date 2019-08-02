package com.example.project2.background.wthreads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GeneralSender extends UdpThread {
    private InetAddress inetAddress;
    private String message = "gRes";
    protected int INTERVAL = 2000;//default


    public GeneralSender()  {
        try {
            inetAddress = InetAddress.getByName("192.168.1.177");
            byte[] messageArray = message.getBytes();
            datagramSocket = new DatagramSocket();
            datagramPacket = new DatagramPacket(messageArray, messageArray.length, inetAddress, PORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void run() {

        while(runnable) {
            try {
                datagramSocket.send(datagramPacket);
                System.out.println("sent packet!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void stopThread() {

        super.stopThread();
        interrupt();
    }
}
