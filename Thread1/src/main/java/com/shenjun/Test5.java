package com.shenjun;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "test5")
public class Test5 {
    private static void SP(int num) {
        try {
            Thread.sleep(num * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("洗水壶");
            SP(1);
            log.debug("烧开水");
            SP(5);
        }, "老王");

        Thread t2 = new Thread(() -> {
            log.debug("洗茶壶");
            SP(1);
            log.debug("洗茶杯");
            SP(2);
            log.debug("拿茶叶");
            SP(1);

            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("泡茶");
        }, "小王");

        t1.start();
        t2.start();
    }


}
