package com.test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Created by anteastra on 12.06.2016.
 */
public class Transfer implements Callable<Boolean> {

    private static int WAIT_SEC = 3;

    private Account from;
    private Account to;
    private int amount;

    public Transfer(Account from, Account to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public Boolean call() throws Exception {

        Lock lock1 = from.getLock();
        Lock lock2 = to.getLock();

        if (lock1.tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
            try {
                // this lock fails often
                if (lock2.tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                    try {
                        if (from.getBalance() < amount) throw new InsufficientFundsException();
                        int randomWaitSec = (new Random().nextInt(5));
                        Thread.sleep(randomWaitSec * 1000);
                        System.out.println("transfer: " + amount + ", from " + from.getName() + " " + from.getBalance() + ", to " + to.getName() + " " + to.getBalance());
                        from.withdraw(amount);
                        to.deposit(amount);
                        return true;
                    } finally {
                        lock2.unlock();
                    }
                } else {
                    to.incFailedTransfer();
                    from.incFailedTransfer();
                    System.out.println("cant lock 'to' account");
                    return false;
                }
            } finally {
                lock1.unlock();
            }
        } else {
            to.incFailedTransfer();
            from.incFailedTransfer();
            System.out.println("cant lock 'from' account");
            return false;
        }
    }
}
