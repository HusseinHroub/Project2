package com.example.project2.background.wthreads;

public class ServiceSenderThread extends GeneralSender {

    public ServiceSenderThread()
    {
        System.out.println("starting Service sender thread");
        start();
    }
    @Override
    public void run() {
        super.run();
        System.out.println("finished service sender");
    }
}
