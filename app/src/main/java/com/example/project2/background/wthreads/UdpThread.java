package com.example.project2.background.wthreads;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpThread extends Thread {
    protected DatagramPacket datagramPacket;
    protected DatagramSocket datagramSocket;
    protected boolean runnable = true;
    protected static final int PORT = 8888;


    public void stopThread() {
        System.out.println("called stopThread in UdpThread");
        runnable = false;
        datagramSocket.close();

    }

}
