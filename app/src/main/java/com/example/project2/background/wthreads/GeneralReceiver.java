package com.example.project2.background.wthreads;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Enumeration;

abstract public class GeneralReceiver extends UdpThread {

    private byte[] buf = new byte[1024];
    private GeneralSender generalSender;
    protected long value;

    private InetAddress localAddress;

    public GeneralReceiver() {

        System.out.println("rec thread is starting");
        datagramPacket = new DatagramPacket(buf, buf.length);
        try {
            datagramSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            getLocalAddress();
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
                if(datagramPacket.getAddress().equals(localAddress))
                    continue;
                String valueString = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("received " + valueString);
                if (valueString.contains(".")) {
//                    valueString = valueString.substring(2);

                    if (generalSender == null) {
                        generalSender = new GeneralSender(valueString);
                        generalSender.start();
                    }
                    else generalSender.setArduinoAddress(valueString);


                    continue;
                }

                try {
                    value = Long.parseLong(valueString);
                    value = bounds(value, 100, 0);
                    value = 100 - value; //this is important!
                    onResultRec();

                } catch (NumberFormatException e) {

                }

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERRRRRO");
            }
        }
    }


    abstract public void onResultRec();

    private long bounds(long value, long maxValue, long minValue) {
        if (value > maxValue)
            return maxValue;
        else if (value < minValue)
            return minValue;
        return value;
    }

    @Override
    public void stopThread() {
        if (generalSender != null)
            generalSender.stopThread();
        super.stopThread();
    }


    protected void sendWhoIsArduino() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("send wia");
                String message = "wiad";
                byte[] messageArray = message.getBytes();
                try {
                    DatagramSocket datagramSocket = new DatagramSocket();
                    InetAddress broadCastIP = InetAddress.getByName("192.168.1.255");
                    DatagramPacket datagramPacket = new DatagramPacket(messageArray, messageArray.length, broadCastIP, PORT);
                    datagramSocket.send(datagramPacket);
                    datagramSocket.close();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }

    private void getLocalAddress() throws SocketException {
        for (
                final Enumeration<NetworkInterface> interfaces =
                NetworkInterface.getNetworkInterfaces();
                interfaces.hasMoreElements();
        ) {

            final NetworkInterface cur = interfaces.nextElement();

            if (cur.isLoopback()) {
                continue;
            }


            for (final InterfaceAddress addr : cur.getInterfaceAddresses()) {
                final InetAddress inet_addr = addr.getAddress();

                if (!(inet_addr instanceof Inet4Address)) {
                    continue;
                }

                localAddress = inet_addr;
                Log.d("ASd", "local address is: " + localAddress.getHostAddress());


            }
        }


    }
}
