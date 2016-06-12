package com.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by anteastra on 11.06.2016.
 */
public class Account {
    private int balance;
    private Lock lock = new ReentrantLock();

    public Account(int balance) {
        this.balance = balance;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public Lock getLock() {
        return lock;
    }

    public int getBalance() {
        return balance;
    }
}
