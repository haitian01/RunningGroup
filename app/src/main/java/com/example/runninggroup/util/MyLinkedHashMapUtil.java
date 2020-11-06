package com.example.runninggroup.util;

import java.util.LinkedHashMap;

public class MyLinkedHashMapUtil<String, Object> extends LinkedHashMap<String, Object> {
    int max = 5;
    public MyLinkedHashMapUtil () {
        super(8, 0.75f,  true);
        this.max = max;
    }

    @Override
    protected boolean removeEldestEntry(Entry<String, Object> eldest) {
        return size() > 5;
    }
}
