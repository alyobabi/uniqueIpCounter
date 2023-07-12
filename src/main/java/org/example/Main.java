package org.example;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        long startTime = System.currentTimeMillis();
        int uniqueIPCount = UniqueIpAddressCounter.getUniqueIPCount(fileName);
        long endTime = System.currentTimeMillis();
        System.out.println("Number of unique IPs: " + uniqueIPCount +
                "\nExecution time: " + (endTime - startTime) + "ms");
    }
}
