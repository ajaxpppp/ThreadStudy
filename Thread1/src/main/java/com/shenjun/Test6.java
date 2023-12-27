package com.shenjun;


import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.locks.Lock;

@Slf4j(topic = "th1")
public class Test6 {

    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decrement();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        log.debug("count = {}", room.getCount());
    }
}

class Room {
    private static int count = 0;

    public void increment() {
        synchronized (Room.class) {
            count++;
        }
    }

    public void decrement() {
        synchronized (Room.class) {
            count--;
        }
    }

    public int getCount() {
        synchronized (Room.class) {
            return count;
        }
    }
}
