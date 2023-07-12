package org.example;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.BitSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class UniqueIpAddressCounter {

    public static Logger LOG = Logger.getLogger(UniqueIpAddressCounter.class.getName());

    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    public static int getUniqueIPCount(String fileName) throws IOException {
        BitSet positiveHashCodeSet = new ConcurrentBitSet();
        BitSet negativeHashCodeSet = new ConcurrentBitSet();

        try (ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE)) {
            try (RandomAccessFile file = new RandomAccessFile(fileName, "r")) {
                long fileSize = file.length();
                long chunkSize = (long) Math.ceil((double) fileSize / THREAD_POOL_SIZE);
                for (int i = 0; i < THREAD_POOL_SIZE; i++) {
                    long startOffset = i * chunkSize;
                    long endOffset = (i + 1) * chunkSize;
                    executorService.execute(new UniqueIpCounterThread(fileName, startOffset, endOffset,
                            positiveHashCodeSet, negativeHashCodeSet));
                }
            }
        }
        return negativeHashCodeSet.cardinality() + positiveHashCodeSet.cardinality();
    }
}