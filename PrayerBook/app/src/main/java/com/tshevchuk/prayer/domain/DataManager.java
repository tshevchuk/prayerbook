package com.tshevchuk.prayer.domain;

import com.tshevchuk.prayer.data.Catalog;

import java.util.Set;

import javax.inject.Inject;

/**
 * Created by taras on 23.03.16.
 */
public class DataManager {
    private final Catalog catalog;

    @Inject
    public DataManager(Catalog catalog) {
        this.catalog = catalog;
    }

    public Set<String> getAllPrayersReferences() {
        return catalog.getAllSources();
    }
}
