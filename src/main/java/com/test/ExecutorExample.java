package com.test;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by anteastra on 12.06.2016.
 */
public class ExecutorExample {

    private static int WAIT_TERMIINATION_SEC = 15;
    private static int MONITOR_DELAY_SEC = 1;

    public static void main(String[] args) throws InterruptedException {

        final Account acc1 = new Account("acc1", 5000);
        final Account acc2 = new Account("acc2", 3000);

        Random rnd = new Random();

        ExecutorService service = Executors.newFixedThreadPool(4);
        for (int i=0; i <= 10; i++) {
            service.submit(new Transfer(acc1, acc2, rnd.nextInt(400)));
        }

        for (int i=0; i <= 10; i++) {
            service.submit(new Transfer(acc2, acc1, rnd.nextInt(400)));
        }

        final ScheduledExecutorService serviceMonitor = Executors.newScheduledThreadPool(1);
        serviceMonitor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("Monitor status: " + LocalDateTime.now());
                System.out.println("failed transfer: " + acc1.getName() + " " + acc1.getFailCounter() + ", " + acc2.getName() + " " + acc2.getFailCounter() );
            }
        }, MONITOR_DELAY_SEC, MONITOR_DELAY_SEC, TimeUnit.SECONDS);

        serviceMonitor.schedule(new Runnable() {
            public void run() {
                serviceMonitor.shutdown();
            }
        }, WAIT_TERMIINATION_SEC, TimeUnit.SECONDS);

        service.shutdown();

        service.awaitTermination(WAIT_TERMIINATION_SEC, TimeUnit.SECONDS);
        serviceMonitor.awaitTermination(WAIT_TERMIINATION_SEC, TimeUnit.SECONDS);
    }
}
