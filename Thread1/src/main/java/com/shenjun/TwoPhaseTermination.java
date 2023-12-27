package com.shenjun;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "main")
public class TwoPhaseTermination {
    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1();
        t1.start();
        Thread.sleep(3500);
        t1.stop();
    }
}

@Slf4j(topic = "th2")
class T1 {
    private Thread mi;

    public void start() {
        mi = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }

                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    current.interrupt();
                }
            }
        });
        mi.start();
    }

    public void stop() {
        mi.interrupt();
    }
}
