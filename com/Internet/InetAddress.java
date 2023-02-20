package com_second.Internet;

import java.net.UnknownHostException;

public class InetAddress {
    public static void main(String[] args) throws UnknownHostException {
        if (args.length > 0){
            String host = args[0];
            java.net.InetAddress[] addresses = java.net.InetAddress.getAllByName(host);
            for (java.net.InetAddress a:addresses){
                System.out.println(a);
            }
        }else {
            java.net.InetAddress localHost = java.net.InetAddress.getLocalHost();
            System.out.println(localHost);
        }
    }
}
