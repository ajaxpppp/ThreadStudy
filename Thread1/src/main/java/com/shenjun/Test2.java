package com.shenjun;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.test1")
public class Test2 {
    public static void main(String[] args) {

        /** 简化前   Runnable a=new Runnable() {
         public void run() {
         log.debug("running1");
         }
         };
         */
            Runnable a = () -> {log.debug("running1");};


        Thread c1 = new Thread(a, "c1");
        c1.start();

    }
}
