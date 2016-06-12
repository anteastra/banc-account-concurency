package com.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Created by anteastra on 12.06.2016.
 *
 * Live lock almost always. hm. How to solve it?
 */
public class LockExample {

    private static int WAIT_SEC = 3;

    public static void main(String[] args) throws InterruptedException {
        final Account a = new Account(2000);
        final Account b = new Account(4000);

        System.out.println("a: " + a.getBalance());
        System.out.println("b: " + b.getBalance());

        new Thread(new Runnable() {
            public void run() {
                try {
                    transfer(a, b, 500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        transfer(b, a, 300);

        System.out.println("a: " + a.getBalance());
        System.out.println("b: " + b.getBalance());
    }

    private static void transfer(Account from, Account to, int amount) throws InsufficientFundsException, InterruptedException {

        Lock lock1 = from.getLock();
        Lock lock2 = to.getLock();

        if (lock1.tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
            try {
                // this lock fails often
                if (lock2.tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                    try {
                        if (from.getBalance() < amount) throw new InsufficientFundsException();
                        System.out.println("transfer: " + amount + ", from " + from.getBalance() + ", to " + to.getBalance());
                        from.withdraw(amount);
                        to.deposit(amount);
                    } finally {
                        lock2.unlock();
                    }
                } else {
                    to.incFailedTransfer();
                    from.incFailedTransfer();
                    System.out.println("cant lock 'to' account");
                }
            } finally {
                lock1.unlock();
            }
        } else {
            to.incFailedTransfer();
            from.incFailedTransfer();
            System.out.println("cant lock 'from' account");
        }

    }
}
