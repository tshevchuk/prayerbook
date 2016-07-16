package com.tshevchuk.prayer.domain;

import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.FileManager;
import com.tshevchuk.prayer.data.PreferenceManager;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.prayer.data.repositories.ChurchCalendarRepository;
import com.tshevchuk.prayer.data.repositories.TextsRepository;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.domain.model.MenuListItem;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

/**
 * Created by taras on 23.03.16.
 */
public class DataManager {
    private final Catalog catalog;
    private final PreferenceManager preferenceManager;
    private final TextsRepository textsRepository;
    private final ChurchCalendarRepository churchCalendarRepository;
    private final FileManager fileManager;

    @Inject
    public DataManager(Catalog catalog, PreferenceManager preferenceManager,
                       TextsRepository textsRepository,
                       ChurchCalendarRepository churchCalendarRepository,
                       FileManager fileManager) {
        this.catalog = catalog;
        this.preferenceManager = preferenceManager;
        this.textsRepository = textsRepository;
        this.churchCalendarRepository = churchCalendarRepository;
        this.fileManager = fileManager;
    }

    public Set<String> getAllPrayersReferences() {
        return catalog.getAllSources();
    }

    public List<MenuListItem> getMenuListItems(int id) {
        return textsRepository.getMenuListItems(id);
    }

    public ArrayList<MenuListItem> getTopMenuListItems() {
        return textsRepository.getTopMenuListItems();
    }

    public ArrayList<MenuListItemOftenUsed> getOftenUsedMenuItems() {
        return textsRepository.getOftenUsedMenuItems();
    }

    public boolean isShowOfficialUGCC() {
        return preferenceManager.isShowOfficialUgccEnabled();
    }

    @DebugLog
    public boolean isHideToolbarOnScrolling() {
        return preferenceManager.isHideToolbarOnScrolling();
    }

    public void updateRecentlyUsedBecauseItemOpened(int id) {
        if (id == Catalog.ID_RECENT_SCREENS) {
            return;
        }
        preferenceManager.markMenuItemAsOpened(id);
    }

    public CalendarDateInfo getCalendarDay(Date date) throws IOException, JSONException {
        return churchCalendarRepository.getCalendarDay(date);
    }

    public ArrayList<CalendarDateInfo> getCalendarDays(int year) throws IOException, JSONException {
        return churchCalendarRepository.getCalendarDays(year);
    }

    public int getTextFontSizeSp() {
        return preferenceManager.getFontSizeSp();
    }

    public ArrayList<MenuListItemSearch> searchMenuItems(String searchPhrase) {
        return textsRepository.searchMenuItems(searchPhrase);
    }

    @DebugLog
    public int[] getYears() throws IOException, JSONException {
        return churchCalendarRepository.getYears();
    }

    @DebugLog
    public int[] getVerifiedYears() throws IOException, JSONException {
        return churchCalendarRepository.getVerifiedYears();
    }

    public MenuItemBase getMenuItem(int id) {
        return textsRepository.getMenuItem(id);
    }

    public MenuListItem getMenuListItem(int id) {
        return textsRepository.getMenuListItem(id);
    }

    public CharSequence loadText(MenuItemPrayer item) {
        return textsRepository.loadText(item);
    }

    public int getDefaultScreenMenuItemId() {
        return preferenceManager.getDefaultMenuItemId();
    }

    public File storeErrorReportScreenshot(byte[] screenshot) {
        return fileManager.storeErrorReportScreenshot(screenshot);
    }

    public File storeErrorReportDeviceInfoAttachment(String s) {
        return fileManager.storeErrorReportDeviceInfoAttachment(s);
    }

    public boolean isNightMode() {
        return preferenceManager.isNightModeEnabled();
    }

    public boolean isKeepScreenOn(){
        return preferenceManager.isKeepScreenOn();
    }
}
