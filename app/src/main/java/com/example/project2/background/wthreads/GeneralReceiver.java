package com.example.project2.background.wthreads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

abstract public class GeneralReceiver extends UdpThread {

    private byte[] buf = new byte[1024];
    protected long value;


    public GeneralReceiver() {

        System.out.println("rec thread is starting");

        datagramPacket = new DatagramPacket(buf, buf.length);
        try {
            datagramSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (runnable) {
            try {
                System.out.println("waiting for message to rec");
                datagramSocket.receive(datagramPacket);
                String valueString = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                value = Long.parseLong(valueString);
                value = bounds(value, 100, 0);
                value = 100 - value; //this is important!
                onResultRec();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERRRRRO");
            }
        }
    }


    abstract public void onResultRec();

    private long bounds(long value, long maxValue, long minValue)
    {
        if(value > maxValue)
            return maxValue;
        else if(value<minValue)
            return minValue;
        return value;
    }

}
