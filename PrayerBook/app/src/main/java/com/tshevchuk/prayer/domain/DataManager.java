package com.tshevchuk.prayer.domain;

import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.PreferenceManager;
import com.tshevchuk.prayer.data.TextsRepository;
import com.tshevchuk.prayer.domain.model.MenuListItem;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by taras on 23.03.16.
 */
public class DataManager {
    private final Catalog catalog;
    private final PreferenceManager preferenceManager;
    private final TextsRepository textsRepository;

    @Inject
    public DataManager(Catalog catalog, PreferenceManager preferenceManager, TextsRepository textsRepository) {
        this.catalog = catalog;
        this.preferenceManager = preferenceManager;
        this.textsRepository = textsRepository;
    }

    public Set<String> getAllPrayersReferences() {
        return catalog.getAllSources();
    }

    public List<MenuListItem> getMenuListItems(int id) {
        return textsRepository.getMenuListItems(id);
    }

    public List<MenuListItem> getTopMenuListItems() {
        return textsRepository.getTopMenuListItems();
    }

    public List<MenuListItemOftenUsed> getOftenUsedMenuItems() {
        return textsRepository.getOftenUsedMenuItems();
    }

    public boolean isShowOfficialUGCC() {
        return preferenceManager.isShowOfficialUgccEnabled();
    }

    public void updateRecentlyUsedBecauseItemOpened(int id) {
        if (id == Catalog.ID_RECENT_SCREENS) {
            return;
        }
        preferenceManager.markMenuItemAsOpened(id);
    }
}
