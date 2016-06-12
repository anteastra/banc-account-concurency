package com.test;

/**
 * Created by anteastra on 11.06.2016.
 *
 * run app and get deadlock
 * use jps to get java process ids
 * use jstack to get threads information
 * use jconsole or jvisualvm to get cool visual info

 */
public class GuarantedDeadlock {
    public static void main(String[] args) throws InterruptedException {
        final Account a = new Account(1000);
        final Account b = new Account(2000);
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

        if (from.getBalance() < amount) throw new InsufficientFundsException();

        System.out.println("try to lock 'from' account");
        synchronized (from) {
            System.out.println("lock 'from' account");
            Thread.sleep(1000);
            System.out.println("try to lock 'to' account");
            synchronized (to) {
                System.out.println("lock 'to' account");
                from.withdraw(amount);
                to.deposit(amount);
            }
            System.out.println("release 'to' account");
        }
        System.out.println("release 'from' account");

        System.out.println("transfer succeed");
    }
}
