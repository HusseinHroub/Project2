package com.example.project2.background;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class MyRecTestThread extends Thread {

    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private byte[] buf = new byte[1024];
    private boolean runnable = true;
    public MyRecTestThread(){

        datagramPacket = new DatagramPacket(buf, buf.length);
        try {
            datagramSocket = new DatagramSocket(8888);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        start();
    }

    @Override
    public void run() {


        while(runnable)
        {
            try {
                System.out.println("waiting for message to rec");
                datagramSocket.receive(datagramPacket);
                String str = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("sensor reading data is: " + str);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERRRRRO");
            }
        }
    }


    public void stopThread()
    {
        runnable = false;
        datagramSocket.close();
    }
}
