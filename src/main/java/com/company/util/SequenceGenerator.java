package com.company.util;

import java.util.concurrent.atomic.AtomicLong;

public class SequenceGenerator {

    private final AtomicLong value;

    public SequenceGenerator() {
        this.value = new AtomicLong(1);
    }

    public SequenceGenerator(long initValue) {
        this.value = new AtomicLong(initValue);
    }

    public long getNext() {
        return value.getAndIncrement();
    }
}
