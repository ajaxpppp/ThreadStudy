package com.shenjun;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ctest3")
public class Test3 {
    public static void main(String[] args) {
        Thread thread = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...123");
            }
        };
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());

    }

}
