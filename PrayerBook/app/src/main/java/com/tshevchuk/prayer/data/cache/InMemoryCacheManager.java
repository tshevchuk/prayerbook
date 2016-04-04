package com.tshevchuk.prayer.data.cache;

/**
 * Created by taras on 04.04.16.
 */
public interface InMemoryCacheManager {
    void putCharSequence(String key, CharSequence text);

    void putCharSequence(String key, CharSequence value, long size);

    CharSequence getCharSequence(String key);
}
