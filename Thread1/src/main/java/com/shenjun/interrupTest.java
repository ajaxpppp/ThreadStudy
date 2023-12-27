package com.shenjun;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c1")
public class interrupTest {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            log.debug("sleep...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "name1");


        thread.start();
        log.debug("123");
        thread.interrupt();
    }
}
