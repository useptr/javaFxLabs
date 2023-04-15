package com.example.fx2.MainScreen.AI;

public abstract class BaseAI extends Thread {
    private volatile boolean isRun = true;
    public abstract void updateCoordinates();
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 / 120);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            System.out.println("run " + getName());
//            System.out.println(getName());
            synchronized (this) {
                if (isRun) {
                    updateCoordinates();
                }
                else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
    }
//    public synchronized void notifyAI() {
//        if (!isRun) {
//            isRun = true;
//            System.out.println("notify");
////            notify();
//        }
//    }
//    public synchronized void stopAI() {
//        if (isRun) {
//            isRun = false;
//            System.out.println("wait");
////            try {
////                wait();
////            } catch (InterruptedException e) {
////                throw new RuntimeException(e);
////            }
//        }
//    }
    public boolean isRunning() {
        return isRun;
    }
    public void setRun(boolean state) {
        isRun = state;
    }
}
