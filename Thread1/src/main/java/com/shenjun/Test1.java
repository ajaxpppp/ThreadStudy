package com.shenjun;


import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test1")
public class Test1 {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    log.debug("running0");
                }

            };
            t.start();
            log.debug("running1");
        }

    }
}
