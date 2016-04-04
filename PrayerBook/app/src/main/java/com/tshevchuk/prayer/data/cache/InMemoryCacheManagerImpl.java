package com.tshevchuk.prayer.data.cache;

import android.util.LruCache;

/**
 * Created by taras on 04.04.16.
 */
public class InMemoryCacheManagerImpl implements InMemoryCacheManager {
    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private final int cacheSize = maxMemory / 8;
    private final LruCache<String, CharSequence> cache = new LruCache<String, String>(cacheSize) {
        @Override
        protected int sizeOf(String key, CharSequence value) {
            return key.length() * 2 + value.length() * 2;
        }
    };

    @Override
    public void putCharSequence(String key, CharSequence text) {
        cache.put(key, text);
    }

    @Override
    public void putCharSequence(String key, CharSequence value, long size) {
        cache.put(key, value, size);

    }

    @Override
    public CharSequence getCharSequence(String key) {
        return cache.get(key);
    }


}
