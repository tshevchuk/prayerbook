package com.tshevchuk.prayer.domain;

import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.PreferenceManager;
import com.tshevchuk.prayer.data.repositories.CerkovnyyCalendarRepository;
import com.tshevchuk.prayer.data.repositories.TextsRepository;
import com.tshevchuk.prayer.domain.model.CalendarDay;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.domain.model.MenuListItem;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;

import java.util.ArrayList;
import java.util.Date;
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
    private final CerkovnyyCalendarRepository cerkovnyyCalendarRepository;

    @Inject
    public DataManager(Catalog catalog, PreferenceManager preferenceManager,
                       TextsRepository textsRepository,
                       CerkovnyyCalendarRepository cerkovnyyCalendarRepository) {
        this.catalog = catalog;
        this.preferenceManager = preferenceManager;
        this.textsRepository = textsRepository;
        this.cerkovnyyCalendarRepository = cerkovnyyCalendarRepository;
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

    public ArrayList<MenuListItemOftenUsed> getOftenUsedMenuItems() {
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

    public CalendarDay getCalendarDay(Date date) {
        return cerkovnyyCalendarRepository.getCalendarDay(date);
    }

    public int getTextFontSizeSp() {
        return preferenceManager.getFontSizeSp();
    }

    public ArrayList<MenuListItemSearch> searchMenuItems(String searchPhrase) {
        return textsRepository.searchMenuItems(searchPhrase);
    }

    public int[] getYears() {
        return cerkovnyyCalendarRepository.getYears();
    }

    public MenuItemBase getMenuItem(int id) {
        return textsRepository.getMenuItem(id);
    }

    public CharSequence loadText(MenuItemPrayer item) {
        return textsRepository.loadText(item);
    }
}
