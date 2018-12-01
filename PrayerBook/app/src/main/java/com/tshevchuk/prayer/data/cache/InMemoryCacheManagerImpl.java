package com.tshevchuk.prayer.data.cache;

import android.util.LruCache;

/**
 * Created by taras on 04.04.16.
 */
public class InMemoryCacheManagerImpl implements InMemoryCacheManager {
    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private final int cacheSize = maxMemory / 8;
    private final LruCache<String, CacheValueWithSize> cache = new LruCache<String, CacheValueWithSize>(cacheSize) {
        @Override
        protected int sizeOf(String key, CacheValueWithSize value) {
            return key.length() * 2 + value.size;
        }
    };

    @Override
    public void putCharSequence(String key, CharSequence text) {
        cache.put(key, new CacheValueWithSize(text, text.length() * 2));
    }

    @Override
    public void putCharSequence(String key, CharSequence value, int size) {
        cache.put(key, new CacheValueWithSize(value, size));
    }

    @Override
    public CharSequence getCharSequence(String key) {
        CacheValueWithSize valueWithSize = cache.get(key);
        if (valueWithSize == null) {
            return null;
        }
        return valueWithSize.value;
    }

    private static class CacheValueWithSize {
        private final int size;
        private final CharSequence value;

        CacheValueWithSize(CharSequence value, int size) {
            this.size = size;
            this.value = value;
        }
    }
}
