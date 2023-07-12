package org.example;

import java.util.BitSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentBitSet extends BitSet {

    private final ReadWriteLock lock;

    public ConcurrentBitSet() {
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public void set(int bitIndex) {
        lock.writeLock().lock();
        try {
            super.set(bitIndex);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean get(int bitIndex) {
        lock.readLock().lock();
        try {
            return super.get(bitIndex);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public int cardinality() {
        lock.readLock().lock();
        try {
            return super.cardinality();
        } finally {
            lock.readLock().unlock();
        }
    }
}
