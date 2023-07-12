package org.example;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.BitSet;
import java.util.logging.Logger;

public record UniqueIpCounterThread(String fileName, long startOffset, long endOffset,
                                    BitSet positiveHashCodeSet,
                                    BitSet negativeHashCodeSet) implements Runnable {

    public static Logger LOG = Logger.getLogger(UniqueIpCounterThread.class.getName());

    @Override
    public void run() {
        try (RandomAccessFile file = new RandomAccessFile(fileName, "r")) {
            seekStartOffset(file);
            while (isEndNotReached(endOffset, file)) {
                String ipAddress = readLine(file);
                if (ipAddress.isBlank()) {
                    continue;
                }
                processIpAddress(ipAddress);
            }
        } catch (IOException e) {
            LOG.warning(e.getMessage());
        }
    }

    private void seekStartOffset(RandomAccessFile file) throws IOException {
        file.seek(startOffset);
        skipToStartOfNextLine(file);
    }

    private void skipToStartOfNextLine(RandomAccessFile file) throws IOException {
        int currentByte = file.read();
        if (currentByte != '\n' && currentByte != -1) {
            skipToStartOfNextLine(file);
        }
    }

    private boolean isEndNotReached(long endOffset, RandomAccessFile file) throws IOException {
        return file.getFilePointer() <= endOffset && file.getFilePointer() < file.length();
    }

    private String readLine(RandomAccessFile file) throws IOException {
        StringBuilder line = new StringBuilder();
        int currentByte;
        while ((currentByte = file.read()) != '\n' && currentByte != -1) {
            line.append((char) currentByte);
        }
        return line.toString().trim();
    }

    private void processIpAddress(String ipAddress) {
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
}
