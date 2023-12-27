package com.shenjun;

import com.sun.org.apache.xpath.internal.functions.FuncTrue;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j(topic = "functrue")
public class FuncTrueTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running123");
                Thread.sleep(3000);
                return 500;
            }
        });

        Thread thread = new Thread(task, "c1");
        thread.start();

        log.debug("{}", task.get());

    }
}
