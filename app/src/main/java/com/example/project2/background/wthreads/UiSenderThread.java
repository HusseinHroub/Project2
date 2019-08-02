package com.example.project2.background.wthreads;

public class UiSenderThread extends GeneralSender {


    public UiSenderThread() {
        System.out.println("UI sender thread is starting");

        start();
    }

    @Override
    public void run() {
        super.run();
        System.out.println("finished UI SENDER");
    }

}
