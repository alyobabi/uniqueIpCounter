package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.BitSet;
import java.util.Objects;
import java.util.logging.Logger;

public class UniqueIpAddressCounter {

    public static Logger LOG = Logger.getLogger(UniqueIpAddressCounter.class.getName());

    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        int uniqueIPCount = getUniqueIPCount(fileName);
        LOG.info("Number of unique IPs: " + uniqueIPCount);
    }

    public static int getUniqueIPCount(String fileName) throws IOException {
        BitSet positiveHashCodeSet = new BitSet(Integer.MAX_VALUE);
        BitSet negativeHashCodeSet = new BitSet(Integer.MAX_VALUE);

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String ipAddress;

        while (Objects.nonNull(ipAddress = reader.readLine()) &&
                !areAllIPAddressesMet(positiveHashCodeSet, negativeHashCodeSet)) {
            if (ipAddress.isBlank()) {
                continue;
            }
            try {
                int ipHashCode = InetAddress.getByName(ipAddress).hashCode();
                if (ipHashCode >= 0) {
                    positiveHashCodeSet.set(ipHashCode);
                } else {
                    negativeHashCodeSet.set(Math.abs(ipHashCode));
                }
            } catch (UnknownHostException ex) {
                LOG.warning(ex.getMessage());
            }
        }
        reader.close();
        return negativeHashCodeSet.cardinality() + positiveHashCodeSet.cardinality();
    }

    private static boolean areAllIPAddressesMet(BitSet positiveHashCodeSet, BitSet negativeHashCodeSet) {
        return positiveHashCodeSet.cardinality() == Integer.MAX_VALUE
                && negativeHashCodeSet.cardinality() == Integer.MAX_VALUE - 1;
    }
}
