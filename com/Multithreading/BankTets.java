package com_second.Multithreading;

public class BankTets {





}

class Bank{
    private Bank(){}

    private static Bank instance = null;

    public static Bank getInstance(){
        if (instance == null) {

            synchronized (Bank.class) {
                if (instance == null) {
                    instance = new Bank();
                }

            }
        }
        return instance;

    }

}
